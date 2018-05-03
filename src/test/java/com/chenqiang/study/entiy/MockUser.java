package com.chenqiang.study.entiy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.chenqiang.study.entity.User;
import com.chenqiang.study.util.Const;
import com.chenqiang.study.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试用户模拟数据配置类
 * 
 * @author qchen
 * @date 2018-5-3
 *
 */
@Component
public class MockUser {
	/**
	 * 引入json文件中的内容
	 */
	@Value(value = "classpath:MockUser.json")
	private Resource resource;
	/**
	 * 定义用于存储用户模拟数据的map(key:模拟用户的类型;value:模拟用户的账号密码)
	 */
	private Map<String, User> mockUserMap = new HashMap<String, User>();
	/**
	 * 自动装配用于操作json的工具类
	 */
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * rsa公钥
	 */
	@Value("${cust.rsa.publicKey}")
	private String publicKey;

	/**
	 * 测试用户账号类型-正常用户
	 */
	public static final String MOCK_USER_ACCT_TYPE_NORMAL = "normalUser";
	/**
	 * 测试用户账号类型-锁定账号用户
	 */
	public static final String MOCK_USER_ACCT_TYPE_LOCKED = "lockedUser";
	/**
	 * 测试用户账号类型-首次登录用户
	 */
	public static final String MOCK_USER_ACCT_TYPE_FIRST_LOGIN = "firstLoginUser";
	/**
	 * 测试用户账号类型-密码过期用户
	 */
	public static final String MOCK_USER_ACCT_TYPE_PWD_EXPIRED = "pwdExpiredUser";
	/**
	 * 测试用户账号类型-不存在的用户
	 */
	public static final String MOCK_USER_ACCT_NO_SUCH = "noSuchUser";
	/**
	 * 测试用户账号类型-密码错误的用户
	 */
	public static final String MOCK_USER_ACCT_PWD_ERR = "pwdErrUser";
	/**
	 * 测试用户账号类型-新增用户(用于测试addUser接口)
	 */
	public static final String MOCK_USER_ACCT_NEW = "newUser";

	@PostConstruct
	public void init() throws Exception {
		// 获取数据
		this.getMockUserData();
	}

	/**
	 * 从json文件中获取所有测试用户数据
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getMockUserData() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		StringBuffer message = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			message.append(line);
		}
		String userDataStr = message.toString();
		// json字符串转map
		Map<String, LinkedHashMap<String, Object>> userDataMap = objectMapper.readValue(userDataStr, Map.class);
		Set<String> keySets = userDataMap.keySet();
		for (String key : keySets) {
			// 密码明文要进行rsa加密
			User user = new User((String) userDataMap.get(key).get(Const.USERID),
					SecurityUtil.encByRSA((String) userDataMap.get(key).get(Const.PWD), publicKey));
			mockUserMap.put(key, user);
		}
	}

	public Map<String, User> getMockUserMap() {
		return mockUserMap;
	}

	public void setMockUserMap(Map<String, User> mockUserMap) {
		this.mockUserMap = mockUserMap;
	}

	/**
	 * 根据用户类型获取用户数据
	 * 
	 * @param userType
	 *            用户类型
	 * @return
	 */
	public TestUser getMockUser(String userType) {
		return new TestUser(this.mockUserMap.get(userType));
	}

}
