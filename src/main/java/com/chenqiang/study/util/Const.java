package com.chenqiang.study.util;

/**
 * 常量类
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
public class Const {
	/** session中用户 */
	public static final String LOGIN_USER = "loginUser";
	/** 数据库中查询出来的用户  */
	public static final String USER_FROM_DB = "userFromDb";
	/** 指定用户名的用户不存在 */
	public static final int USER_WITH_NAME_NOT_EXISTS = -2;
	/** 指定用户名的用户已存在 */
	public static final int USER_WITH_NAME_ALREADY_EXISTS = -3;
	/** 指定用户id的用户不存在 */
	public static final int USER_WITH_USERID_NOT_EXISTS = -4;
	/** 指定用户id的用户已存在 */
	public static final int USER_WITH_USERID_ALREADY_EXISTS = -5;
	/** 用户已锁定 */
	public static final int USER_IS_LOCKEDIN = -6;
	/** 用户密码已过期 */
	public static final int USER_PWD_EXPIRED = -7;
	/** 用户首次登录,请修改密码 */
	public static final int USER_FIRST_LOGIN_PLEASE_MODIFY_PWD = -8;
	/** 用户登录成功 */
	public static final int USER_LOGIN_SUCCESS = 100;
}
