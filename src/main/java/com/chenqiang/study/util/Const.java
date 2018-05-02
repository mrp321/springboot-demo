package com.chenqiang.study.util;

/**
 * 常量类
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
public class Const {
	//-------------------------------一般性的常量----------------------------
	/** session中用户 */
	public static final String LOGIN_USER = "loginUser";
	/** 数据库中查询出来的用户 */
	public static final String USER_FROM_DB = "userFromDb";
	/** 接口测试jsonPath取接口返回内容中的success */
	public static final String JSON_PATH_SUCCESS = "$.success";
	/** 接口测试jsonPath取接口返回内容中的data */
	public static final String JSON_PATH_DATA = "$.data";
	/** 接口测试jsonPath取接口返回内容中的msg */
	public static final String JSON_PATH_MSG = "$.msg";
	//--------------------------------代码逻辑中的一些标志位-----------------------------------
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
	/** 用户密码错误 */
	public static final int USER_WRONG_PWD = -9;
	/** 用户登录成功 */
	public static final int USER_LOGIN_SUCCESS = 100;
	/** 用户账号状态-未锁定 */
	public static final int USER_ACCOUNT_STATUS_NOT_LOCKED = 0;
	/** 用户账号状态-已锁定 */
	public static final int USER_ACCOUNT_STATUS_LOCKED = 1;
	//---------------------------------接口地址--------------------------------------------
	/** 用户登陆url */
	public static final String URL_LOGIN = "/user/login";
	/** 用户注销url */
	public static final String URL_LOGOUT = "/user/logout";
	/** 用户增加url */
	public static final String URL_ADD_USER = "/user/addUser";
	/** 用户删除url */
	public static final String URL_DEL_USER = "/user/delUser";
	/** 用户修改url */
	public static final String URL_MODI_USER = "/user/modiUser";
	/** 用户查询url */
	public static final String URL_QUERY_USER = "/user/queryUser";
	//--------------------------------接口返回提示信息--------------------------------------------
	/** 首次登陆时的提示信息 */
	public static final String MSG_FIRST_LOGIN_MINUS8 = "首次登录,请修改密码";
	/** 用户锁定时的提示信息 */
	public static final String MSG_LOCKIN_MINUS6 = "当前用户已锁定";
	/** 用户密码过期时的提示信息 */
	public static final String MSG_PWD_EXPIRED_MINUS7 = "用户密码已过期, 请修改密码";
	/** 用户密码错误时的提示信息 */
	public static final String MSG_PWD_WRONG_MINUS9 = "用户密码错误";
	/** 指定用户已存在提示信息 */
	public static final String MSG_USER_EXISTS_MINUS5 = "指定用户已存在";
	/** 指定用户不存在提示信息 */
	public static final String MSG_USER_NOT_EXISTS_MINUS4 = "指定用户不存在";
	/** 无session时的提示信息 */
	public static final String MSG_NO_SESSION = "您尚未登录,或者登录会话已过期";
	/** 系统异常时的提示信息 */
	public static final String MSG_EXCEPTION = "系统异常";
	/** 传入参数为空时的提示信息 */
	public static final String MSG_NO_PARAMS = "传入参数不能为空";
	/** 用户登陆成功提示信息 */
	public static final String MSG_LOGIN_SUCCESS = "用户登陆成功";
	/** 用户登陆失败提示信息 */
	public static final String MSG_LOGIN_FAIL = "用户登陆失败";
	/** 用户注销成功提示信息 */
	public static final String MSG_LOGOUT_SUCCESS = "用户注销成功";
	/** 用户添加成功提示信息 */
	public static final String MSG_ADD_SUCCESS = "用户添加成功";
	/** 用户添加失败提示信息 */
	public static final String MSG_ADD_FAIL = "用户添加失败";
	/** 用户删除成功提示信息 */
	public static final String MSG_DEL_SUCCESS = "用户删除成功";
	/** 用户删除失败提示信息 */
	public static final String MSG_DEL_FAIL = "用户删除失败";
	/** 用户修改成功提示信息 */
	public static final String MSG_MODI_SUCCESS = "用户修改成功";
	/** 用户修改失败提示信息 */
	public static final String MSG_MODI_FAIL = "用户修改失败";
	/** 用户查询成功提示信息 */
	public static final String MSG_QUERY_SUCCESS = "用户查询成功";
	/** 用户查询失败提示信息 */
	public static final String MSG_QUERY_FAIL = "用户查询失败";

}
