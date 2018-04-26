package com.chenqiang.study.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.chenqiang.study.dao.UserDao;
import com.chenqiang.study.entity.User;

import cn.hutool.core.util.StrUtil;

@Repository
public class UseDaoTest implements UserDao {
	private List<User> userList = new ArrayList<User>();

	@Override
	public User login(String userId, String pwd) {
		return this.queryUserByUserId(userId);
	}

	@Override
	public int addUser(User user) {
		userList.add(user);
		return 1;
	}

	@Override
	public int delUser(String userId) {
		// 查询用户
		User user = this.queryUserByUserId(userId);
		// 移除用户
		userList.remove(user);
		return 1;
	}

	@Override
	public int modiUser(String userId, String pwd) {
		// 设置用户
		User user = new User();
		user.setUserId(userId);
		// 修改用户
		this.modiUserInfoByUserId(user);
		return 1;

	}

	@Override
	public List<User> queryUser(String userId) {
		// 用户id为空,默认返回所有用户
		// 用户id不为空,则根据用户id查
		if (StrUtil.isNotBlank(userId)) {
			return this.userList;
		} else {
			User userRtn = this.queryUserByUserId(userId);
			if (userRtn != null) {
				List<User> userListRtn = new ArrayList<User>();
				userListRtn.add(userRtn);
				return userListRtn;
			} else {
				return null;
			}
		}

	}

	@Override
	public User queryUserByUserId(String userId) {
		User userRtn = null;
		// 遍历用户
		for (User user : userList) {
			// 如果存在,直接返回
			String userIdExist = user.getUserId();
			if (userIdExist.equals(userId)) {
				userRtn = user;
			}
		}
		return userRtn;
	}

	@Override
	public int modiUserInfoByUserId(User user) {
		// 删除用户
		this.delUser(user.getUserId());
		// 新增用户
		this.userList.add(user);
		return 1;
	}

	@Override
	public int addPwdRec(String userId, String pwdRec) {
		return 1;
	}

}
