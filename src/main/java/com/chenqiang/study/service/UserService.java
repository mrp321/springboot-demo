package com.chenqiang.study.service;

import java.util.List;
import java.util.Map;

import com.chenqiang.study.entity.User;

/**
 * 用户业务层
 * 
 * @author QChen
 * @date 2018-4-13
 *
 */
public interface UserService {

	/**
	 * 用户登录
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * 
	 * @param name
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	User login(String userId, String pwd);

	/**
	 * 添加用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @param age
	 *            年龄
	 * @param name
	 *            姓名
	 * @return
	 */
	int addUser(String userId, String pwd, String name, Integer age);

	/**
	 * 删除用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	int delUser(String userId);

	/**
	 * 修改用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * 
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param age
	 *            年龄
	 * @param name
	 *            姓名
	 * @return
	 */
	int modiUser(String userId, String pwd, String name, Integer age);

	/**
	 * 查询用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param userId
	 *            用户id(可选)
	 * @return
	 */
	List<User> queryUser(String userId);

	/**
	 * 获取登录的返回信息
	 * 
	 * @author qchen
	 * @date 2018-4-17
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	Map<String, Object> getLoginMap(String userId, String pwd);

}
