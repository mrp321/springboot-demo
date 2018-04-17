package com.chenqiang.study.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 共通controller
 * 
 * @author QChen
 * @date 2018-4-11
 *
 */
public class CommController {
	/**
	 * 返回信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * @param success
	 *            成功
	 * @param data
	 *            数据
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getRtnMap(boolean success, Object data, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		if (data != null) {
			map.put("data", data);
		}
		map.put("msg", msg);
		return map;
	}

	/**
	 * 返回信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * @param success
	 *            成功
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getRtnMap(boolean success, String msg) {
		return this.getRtnMap(success, null, msg);
	}

	/**
	 * 返回成功信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * 
	 * @param data
	 *            数据
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getSuccRtnMap(Object data, String msg) {
		return this.getRtnMap(true, data, msg);
	}

	/**
	 * 返回成功信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getSuccRtnMap(String msg) {
		return this.getSuccRtnMap(null, msg);
	}

	/**
	 * 返回失败信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * @param data
	 *            数据
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getFailRtnMap(Object data, String msg) {
		return this.getRtnMap(false, data, msg);
	}

	/**
	 * 返回失败信息
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * 
	 * @param msg
	 *            提示信息
	 * @return 返回信息map
	 */
	public Map<String, Object> getFailRtnMap(String msg) {
		return this.getFailRtnMap(null, msg);
	}
}
