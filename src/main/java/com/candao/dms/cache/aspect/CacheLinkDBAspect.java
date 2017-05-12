package com.candao.dms.cache.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.candao.dms.cache.annotation.CacheLinkDB;
import com.candao.dms.cache.bean.CacheKey;
import com.candao.dms.cache.utils.ObjectUtil;

/**
 * @author jeromeLiu
 * 
 *         Cache<==>DB 联动切面
 */
@Component
@Aspect
public class CacheLinkDBAspect {

	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	@Pointcut("@annotation(com.candao.dms.cache.annotation.CacheLinkDB)")
	public void checkCache() {
	}

	private StringBuffer CONST_REDIS_KEY = new StringBuffer();

	@Around("checkCache()")
	public Object checkCache(ProceedingJoinPoint pjp) throws Throwable {
		// 获取自定义注解头部信息
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		CacheLinkDB action = method.getAnnotation(CacheLinkDB.class);

		Class<?> classType = Class.forName(action.keyClassType());

		CONST_REDIS_KEY = getCacheKey(pjp, action);

		// 先行判定操作指令
		switch (action.OP()) {
		case SELECT:
			// 断言对象基础类型
			if (classType.getTypeName() instanceof String) {
				Object value = redisTemplate.opsForValue().get(CONST_REDIS_KEY.toString());
				if (value != null && StringUtils.isNotEmpty(value.toString())
						&& StringUtils.isNotBlank(value.toString())) {
					return value;
				}
			}
			break;
		case DEL:
			redisTemplate.delete(CONST_REDIS_KEY.toString());
			break;
		case UPDATE:
			redisTemplate.delete(CONST_REDIS_KEY.toString());
			break;
		case SAVE:
			//保险操作,先进行清理操作
			redisTemplate.delete(CONST_REDIS_KEY.toString());
			System.out.println("set操作,无须做任何操作.");
			break;
		}

		// 执行正常的业务操作
		return pjp.proceed();
	}

	/**
	 * @param jp
	 *            切面对象
	 * @param rtv
	 *            执行结果返回值
	 */
	@AfterReturning(pointcut = "checkCache()", returning = "rtv")
	public void saveDataWithCache(JoinPoint jp, Object rtv) {
		Object value = redisTemplate.opsForValue().get(CONST_REDIS_KEY.toString());
		// 断言redis是否存在key
		if (value == null) {
			redisTemplate.opsForValue().set(CONST_REDIS_KEY.toString(), ObjectUtil.toString(rtv));
		}
	}

	/**
	 * 构造cacheKey结构
	 * 
	 * @param pjp
	 * @param action
	 * @return
	 * @throws Throwable
	 */
	private StringBuffer getCacheKey(ProceedingJoinPoint pjp, CacheLinkDB action) throws Throwable {
		// 递归方法形参
		for (Object object : pjp.getArgs()) {
			Boolean isSuperClass = CacheKey.class.isAssignableFrom(object.getClass().getSuperclass());
			// 1.先断言是否存在CacheKey对象 的父类
			if (!isSuperClass) {

				// 该处说明下,必须是自身的对象携带id列段,通过继承的方式是无效的(暂不考虑继承的方案).
				Method getIdMethod = object.getClass().getMethod("getId");
				// 1.1 断言是否存在id列段
				if (getIdMethod == null)
					continue;
				// 1.2 反射执行函数获取返回值
				String id = (String) getIdMethod.invoke(object);

				CacheLinkDB cacheLinkDB = object.getClass().getAnnotation(CacheLinkDB.class);
				// 1.3 断言对象是否存在指定的注解对象CacheLinkDB
				if (cacheLinkDB != null) {
					CONST_REDIS_KEY.append(cacheLinkDB.keyPre()).append("_").append(id);
				} else {
					// 1.4 使用外围方法的keyPre
					CONST_REDIS_KEY.append(action.keyPre()).append("_").append(id);
				}
				break;
			} else {
				// 2.反射执行函数获取返回值
				Method getCacheKeyMethod = object.getClass().getMethod("getCacheKey");
				String cacheKey = (String) getCacheKeyMethod.invoke(object);
				CONST_REDIS_KEY.append(cacheKey);
				break;
			}
		}
		return CONST_REDIS_KEY;
	}

}