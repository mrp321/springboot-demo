package com.chenqiang.study.entiy;

import org.springframework.mock.web.MockHttpSession;

import com.chenqiang.study.entity.User;

/**
 * 测试用户类
 * 
 * @author qchen
 * @date 2018-5-3
 *
 */
public class TestUser {
	/**
	 * 用户信息
	 */
	private User user;
	/**
	 * 用户session
	 */
	private MockHttpSession session;

	public TestUser() {
		super();
	}

	public TestUser(User user) {
		super();
		this.user = user;
	}

	public TestUser(User user, MockHttpSession session) {
		super();
		this.user = user;
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MockHttpSession getSession() {
		return session;
	}

	public void setSession(MockHttpSession session) {
		this.session = session;
	}

	public String getUserId() throws Exception {
		if (user == null) {
			throw new Exception("用户信息为空");
		}
		return user.getUserId();
	}

	public void setUserId(String userId) throws Exception {
		if (user == null) {
			throw new Exception("用户信息为空");
		}
		user.setUserId(userId);
	}

	public String getPwd() throws Exception {
		if (user == null) {
			throw new Exception("用户信息为空");
		}
		return user.getPwd();
	}

	public void setPwd(String pwd) throws Exception {
		if (user == null) {
			throw new Exception("用户信息为空");
		}
		user.setPwd(pwd);
	}
}
