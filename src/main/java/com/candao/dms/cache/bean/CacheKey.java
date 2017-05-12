package com.candao.dms.cache.bean;

/**
 * 
 * 
 * @author jeromeLiu
 * @version 1.0.0 2017年5月11日 下午3:10:24
 */
public abstract class CacheKey {

	/**
	 * key 规则 ： xxx:id(code)
	 */

	public abstract String getCacheKey();

}
