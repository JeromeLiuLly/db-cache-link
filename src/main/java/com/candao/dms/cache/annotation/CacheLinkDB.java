package com.candao.dms.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.candao.dms.cache.bean.CacheOPEnum;

/**
 * 自定义注解,进行DB和Cache的联动处理
 * 
 * @Retention 用于描述注解的生命周期
 * @Target 用于描述注解的使用范围
 * 
 * @author jeromeLiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CacheLinkDB {

	/**
	 * 缓存key值前缀
	 * 
	 * @return
	 */
	String keyPre();

	/**
	 * 缓存key的数据结构,默认是字符串类型
	 * 
	 * 结合当前的业务需求,仅支持字符串类型处理
	 * 
	 * @return
	 */
	String keyClassType() default "java.lang.String";
	
	/**
	 * 操作指令(SELECT,DEL,UPDATE),仅作用在方法头部有效
	 * 
	 * @return
	 */
	CacheOPEnum OP() default CacheOPEnum.SELECT;

	/**
	 * 缓存key的失效时间(s),默认失效时间：600s
	 * 
	 * @return
	 */
	int expire() default 600;

}
