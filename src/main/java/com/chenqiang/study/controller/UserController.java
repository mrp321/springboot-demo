package com.chenqiang.study.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chenqiang.study.entity.User;
import com.chenqiang.study.service.UserService;
import com.chenqiang.study.util.Const;

import cn.hutool.core.util.StrUtil;

/**
 * 用户控制层
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
@RestController
@RequestMapping("user")
public class UserController extends CommController {
	/** 用户业务层 */
	@Autowired
	private UserService userService;
	/** 日志 */
	private static Logger log = Logger.getLogger(UserController.class.getName());

	/**
	 * 用户登录
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	@PostMapping("login")
	public Map<String, Object> login(HttpSession session, String userId, String pwd) {
		log.info("登录开始");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StrUtil.isBlank(userId) || StrUtil.isBlank(pwd)) {
			return this.getFailRtnMap(Const.MSG_NO_PARAMS);
		}
		try {
			Map<String, Object> userInfoMap = this.userService.getLoginMap(userId, pwd);
			// 获取操作结果flag
			int flag = (int) userInfoMap.get("flag");
			if (flag == Const.USER_FIRST_LOGIN_PLEASE_MODIFY_PWD) {
				session.setAttribute(Const.LOGIN_USER, userInfoMap.get(Const.USER_FROM_DB));
				map = this.getFailRtnMap(flag, Const.MSG_FIRST_LOGIN_MINUS8);
			} else if (flag == Const.USER_IS_LOCKEDIN) {
				map = this.getFailRtnMap(flag, Const.MSG_LOCKIN_MINUS6);
			} else if (flag > 0) {
				session.setAttribute(Const.LOGIN_USER, userInfoMap.get(Const.USER_FROM_DB));
				map = this.getSuccRtnMap(flag, Const.MSG_LOGIN_SUCCESS);
			} else if (flag == Const.USER_PWD_EXPIRED) {
				session.setAttribute(Const.LOGIN_USER, userInfoMap.get(Const.USER_FROM_DB));
				map = this.getFailRtnMap(flag, Const.MSG_PWD_EXPIRED_MINUS7);
			} else if (flag == Const.USER_WRONG_PWD) {
				map = this.getFailRtnMap(flag, Const.MSG_PWD_WRONG_MINUS9);
			} else if (flag == Const.USER_WITH_USERID_NOT_EXISTS) {
				map = this.getFailRtnMap(flag, Const.MSG_USER_NOT_EXISTS_MINUS4);
			} else {
				map = this.getFailRtnMap(flag, Const.MSG_LOGIN_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("返回错误信息:" + e.getMessage());
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("登录结束");
		return map;
	}

	/**
	 * 注销
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * 
	 * @return
	 */
	@PostMapping("logout")
	public Map<String, Object> logout(HttpSession session) {
		log.info("注销开始");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object user = session.getAttribute(Const.LOGIN_USER);
			if (user != null) {
				session.removeAttribute(Const.LOGIN_USER);
			}
			map = this.getSuccRtnMap(Const.MSG_LOGOUT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("返回错误信息:" + e.getMessage());
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("注销结束");
		return map;
	}

	/**
	 * 添加用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	@PostMapping("addUser")
	public Map<String, Object> addUser(HttpSession session, String userId, String pwd, String name, Integer age) {
		log.info("添加用户开始");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StrUtil.isBlank(userId) || StrUtil.isBlank(pwd)) {
			return this.getFailRtnMap(Const.MSG_NO_PARAMS);
		}
		try {
			Object user = session.getAttribute(Const.LOGIN_USER);
			if (user == null) {
				return this.getFailRtnMap(Const.MSG_NO_SESSION);
			}
			int count = this.userService.addUser(userId, pwd, name, age);
			if (count > 0) {
				map = this.getSuccRtnMap(Const.MSG_ADD_SUCCESS);
			} else if (count == Const.USER_WITH_USERID_ALREADY_EXISTS) {
				map = this.getFailRtnMap(Const.MSG_USER_EXISTS_MINUS5);
			} else {
				map = this.getFailRtnMap(Const.MSG_ADD_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("返回错误信息:" + e.getMessage());
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("添加用户结束");
		return map;
	}

	/**
	 * 删除用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * @param userId
	 *            用户id
	 * @return
	 */
	@PostMapping("delUser")
	public Map<String, Object> delUser(HttpSession session, String userId) {
		log.info("删除用户开始");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StrUtil.isBlank(userId)) {
			return this.getFailRtnMap(Const.MSG_NO_PARAMS);
		}
		try {
			Object user = session.getAttribute(Const.LOGIN_USER);
			if (user == null) {
				return this.getFailRtnMap(Const.MSG_NO_SESSION);
			}
			int count = this.userService.delUser(userId);
			if (count > 0) {
				map = this.getSuccRtnMap(Const.MSG_DEL_SUCCESS);
			} else {
				map = this.getFailRtnMap(Const.MSG_DEL_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("返回错误信息:" + e.getMessage());
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("删除用户结束");
		return map;
	}

	/**
	 * 修改用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	@PostMapping("modiUser")
	public Map<String, Object> modiUser(HttpSession session, String userId, String pwd, String name, Integer age) {
		log.info("修改用户开始");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StrUtil.isBlank(userId)) {
			return this.getFailRtnMap(Const.MSG_NO_PARAMS);
		}
		try {
			Object user = session.getAttribute(Const.LOGIN_USER);
			if (user == null) {
				return this.getFailRtnMap(Const.MSG_NO_SESSION);
			}
			int count = this.userService.modiUser(userId, pwd, name, age);
			if (count > 0) {
				map = this.getSuccRtnMap(Const.MSG_MODI_SUCCESS);
			} else if (count == Const.USER_WITH_USERID_NOT_EXISTS) {
				map = this.getFailRtnMap(Const.MSG_USER_NOT_EXISTS_MINUS4);
			} else {
				map = this.getFailRtnMap(Const.MSG_MODI_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("返回错误信息:" + e.getMessage());
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("修改用户结束");
		return map;
	}

	/**
	 * 查询用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param session
	 *            会话
	 * @param userId
	 *            用户id(可选)
	 * @return
	 */
	@GetMapping("queryUser")
	public Map<String, Object> queryUser(HttpSession session, String userId) {
		log.info("查询用户开始");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object user = session.getAttribute(Const.LOGIN_USER);
			if (user == null) {
				return this.getFailRtnMap(Const.MSG_NO_SESSION);
			}
			List<User> userList = this.userService.queryUser(userId);
			map = this.getSuccRtnMap(userList, Const.MSG_QUERY_SUCCESS);
		} catch (Exception e) {
			log.severe("返回错误信息:" + e.getMessage());
			e.printStackTrace();
			map = this.getFailRtnMap(Const.MSG_EXCEPTION);
		}
		log.info("查询用户结束");
		return map;
	}

}
