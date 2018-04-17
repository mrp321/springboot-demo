package com.chenqiang.study.provider;

import org.apache.ibatis.jdbc.SQL;

import cn.hutool.core.util.StrUtil;

/**
 * 用户动态SQL
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
public class UserProvider {
	/**
	 * 用户查询
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public String queryUser(String userId) {
		return new SQL() {
			{
				//SELECT("userId, name, pwd, sex, age, pwdRec, pwdErrCout, pwdExpiredDate, lastLoginDate, isLock, version");
				SELECT("userId, name, sex, age, pwdErrCout, pwdExpiredDate, lastLoginDate, isLock, version");
				FROM("user");
				if (StrUtil.isNotBlank(userId)) {
					WHERE("userId = #{userId}");
				}
			}
		}.toString();
	}
}
