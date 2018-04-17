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
				String selectSql = null;
				// 如果是查询所有用户信息,则过滤密码信息
				if (StrUtil.isNotBlank(userId)) {
					selectSql = "name, pwd, sex, age, pwdRec, pwdErrCout, lastLoginDate, isLock, version";
				} else {
					selectSql = "name, sex, age, pwdErrCout, lastLoginDate, isLock, version";
				}
				SELECT(selectSql);
				FROM("user");
				if (StrUtil.isNotBlank(userId)) {
					WHERE("userId = #{userId}");
				}
			}
		}.toString();
	}
}
