package com.tung7.ex.repository.web;

/**
 * Handler处理类的预处理接口
 * @author Wayen
 *
 */
public interface PreHandler {

	/**
	 * 根据urlPath判断是否可以处理
	 * @param urlPath
	 * @return true 此Handler将继续处理
	 * 			false 此Handler将忽略
	 */
	public boolean applyPreHandle(String urlPath);

}