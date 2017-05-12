package com.candao.dms.cache.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.candao.dms.cache.annotation.CacheLinkDB;
import com.candao.dms.cache.bean.CacheOPEnum;
import com.candao.dms.cache.bean.JeromeLiu;

/**
 * @author jeromeLiu
 */
@Service
public class JeromeLiuTest {

	@CacheLinkDB(keyPre = "jeromeLiu", OP = CacheOPEnum.SELECT, keyClassType = "java.lang.String")
	public String testAnnotation() {
		return "查询数据库";
	}

	/**
	 * 测试查询行为
	 * 
	 * @param jeromeLiu
	 * @return
	 */
	@CacheLinkDB(keyPre = "jeromeLiu", OP = CacheOPEnum.SELECT, keyClassType = "java.lang.String")
	public String testAnnotation2(JeromeLiu jeromeLiu) {
		System.out.println("走DB：我是查询行为");
		return JSONObject.toJSONString(jeromeLiu);
	}
	
	/**
	 * 测试更新行为
	 * 
	 * @param jeromeLiu
	 * @return
	 */
	@CacheLinkDB(keyPre = "jeromeLiu", OP = CacheOPEnum.UPDATE, keyClassType = "java.lang.String")
	public String testAnnotation3(JeromeLiu jeromeLiu) {
		System.out.println("走DB：我是更新行为");
		return JSONObject.toJSONString(jeromeLiu);
	}
	
	/**
	 * 测试删除行为
	 * 
	 * @param jeromeLiu
	 * @return
	 */
	@CacheLinkDB(keyPre = "jeromeLiu", OP = CacheOPEnum.DEL, keyClassType = "java.lang.String")
	public String testAnnotation4(JeromeLiu jeromeLiu) {
		System.out.println("走DB：我是删除行为");
		return JSONObject.toJSONString(jeromeLiu);
	}

}
