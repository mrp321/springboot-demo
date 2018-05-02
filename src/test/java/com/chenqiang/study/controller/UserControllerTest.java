package com.chenqiang.study.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.chenqiang.study.util.Const;

/**
 * 用户控制层测试类
 * 
 * @author qchen
 * @date 2018-4-26
 *
 */
// @RunWith指定测试用例的运行器
@RunWith(SpringRunner.class)
// @SpringBootTest指定当前类是一个测试类
@SpringBootTest
@Transactional
public class UserControllerTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	// @Autowired
	// private UserController userController;
	@Autowired
	private MockHttpSession session;
	private ResultActions resultActions;
	/**
	 * 進行模擬請求時需要傳入的參數
	 */
	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	/**
	 * 用户id
	 */
	private String userId = "test2";
	/**
	 * 密码
	 */
	private String pwd = "K1/GHPRBIKhzhBLZNQeKu4L3WQJTLdh5xYQpn6w/l6bfDDo+zHeJ7EKrD1f1GD5wx/70VMFJbFAj9LsYG6uG05eFaFNuDaqwio63/Nx2i9sVXf0prFMMBZyX2tpmGVVtEW+OkwyAEeB8MKucuNloGNGW09ghBBD6edGZa+ve3fE=";

	/**
	 * 前置操作
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// 方式1：@Autowired注解装配UserController
		// mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		// 方式2：@Autowired注解装配WebApplicationContext
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		// 模拟登录,设置session
		this.setLoginSession(userId, pwd);
	}

	/**
	 * 测试登录成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginSuccess() throws Exception {
		resultActions.andExpect(MockMvcResultMatchers.jsonPath(Const.JSON_PATH_SUCCESS).value(true));
	}

	/**
	 * 測試登陸時未傳入完整參數
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailWithoutParams() throws Exception {
		params.add("userId", userId);
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_MSG, Const.MSG_NO_PARAMS);
	}

	@Test
	public void testLoginFailUserLockin() throws Exception {
		resultActions.andExpect(MockMvcResultMatchers.jsonPath(Const.JSON_PATH_DATA).value(Const.USER_IS_LOCKEDIN));
	}

	/**
	 * 测试注销成功
	 * 
	 * @throws Exception
	 */
	// @Test
	public void testLogoutSuccess() throws Exception {
		this.perform(HttpMethod.POST, Const.URL_LOGOUT, params, Const.JSON_PATH_SUCCESS, true);
	}

	/**
	 * 测试删除
	 * 
	 * @throws Exception
	 */
	// @Test
	public void testDelUserSuccess() throws Exception {
		params.add("userId", userId);
		this.perform(HttpMethod.POST, Const.URL_DEL_USER, params, Const.JSON_PATH_SUCCESS, true);
	}

	/**
	 * 測試刪除，無session
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelUserFailWithoutSession() throws Exception {
		params.add("userId", userId);
		this.perform(HttpMethod.POST, Const.URL_DEL_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_SESSION);
	}

	/**
	 * 测试修改成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserSuccess() throws Exception {

		params.add("userId", userId);
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_SUCCESS, true);
	}

	/**
	 * 测试修改成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserFailWithoutParams() throws Exception {

		params.add("userId", userId);
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_DATA, true);
	}

	/**
	 * 测试查询成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueryUserSuccess() throws Exception {
		params.add("userId", userId);
		this.perform(HttpMethod.GET, Const.URL_QUERY_USER, params, Const.JSON_PATH_SUCCESS, true);
	}

	/**
	 * 设置登陸session
	 * 
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @throws Exception
	 */
	private void setLoginSession(String userId, String pwd) throws Exception {
		params.add("userId", userId);
		params.add("pwd", pwd);
		// 模拟登录,设置session
		this.performReturnResultActions(HttpMethod.POST, Const.URL_LOGIN, params);
		// 获取接口返回的结果
		MvcResult result = resultActions.andReturn();
		// 设置session
		this.session = (MockHttpSession) result.getRequest().getSession();
	}

	/**
	 * 模擬接口測試
	 * 
	 * @param method
	 *            方法
	 * @param url
	 *            接口地址
	 * @param params
	 *            参数
	 * @param jsonPath
	 *            表达式
	 * @param value
	 *            匹配值
	 * @throws Exception
	 */
	private void perform(HttpMethod method, String url, MultiValueMap<String, String> params, String jsonPath,
			Object value) throws Exception {
		this.performReturnResultActions(method, url, params);
		resultActions.andExpect(MockMvcResultMatchers.jsonPath(jsonPath).value(value));
	}

	/**
	 * 模拟接口测试
	 * 
	 * @param method
	 *            方法
	 * @param url
	 *            接口地址
	 * @param params
	 *            参数
	 * @throws Exception
	 */
	private void performReturnResultActions(HttpMethod method, String url, MultiValueMap<String, String> params)
			throws Exception {
		MockHttpServletRequestBuilder builder = null;
		if (method == HttpMethod.GET) {
			builder = MockMvcRequestBuilders.get(url);
		} else if (method == HttpMethod.POST) {
			builder = MockMvcRequestBuilders.post(url);
		}
		resultActions = mockMvc.perform(builder.param("userId", userId).params(params).session(session))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
	}

}
