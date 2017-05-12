package com.candao.dms.cache.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.candao.dms.cache.annotation.CacheLinkDB;

/**
 * @author jeromeLiu
 * @version 1.0.0 2017年5月11日 下午3:13:32
 */
@CacheLinkDB(keyPre = "jeromeLiu", keyClassType = "java.lang.String")
public class JeromeLiu extends CacheKey {

	private String id;

	private String name;

	private Integer age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	@JSONField(serialize=false)  
	public String getCacheKey() {
		return "jeromeLiu:id:" + getId();
	}

}
