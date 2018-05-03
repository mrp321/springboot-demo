package com.chenqiang.study.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

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

import com.chenqiang.study.entiy.MockUser;
import com.chenqiang.study.entiy.TestUser;
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
	/**
	 * 测试用户数据
	 */
	@Autowired
	private MockUser mockUser;
	/**
	 * 正常用户
	 */
	private TestUser normalUser;
	/**
	 * 账号锁定用户
	 */
	private TestUser lockedUser;
	/**
	 * 首次登录用户
	 */
	private TestUser firstLoginUser;
	/**
	 * 密码过期用户
	 */
	private TestUser pwdExpiredUser;
	/**
	 * 不存在用户
	 */
	private TestUser noSuchUser;
	/**
	 * 密码错误用户
	 */
	private TestUser pwdErrUser;
	/**
	 * 新增用户
	 */
	private TestUser newUser;

	/**
	 * 進行模擬請求時需要傳入的參數
	 */
	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(UserControllerTest.class.getName());
	/**
	 * 是否在控制台打印一些参数
	 */
	private boolean doPrint = false;

	public void init() throws Exception {
		log.info("获取所有测试用户数据");
		List<TestUser> userList = new ArrayList<TestUser>();
		normalUser = normalUser != null ? normalUser : mockUser.getMockUser(MockUser.MOCK_USER_ACCT_TYPE_NORMAL);
		lockedUser = lockedUser != null ? lockedUser : mockUser.getMockUser(MockUser.MOCK_USER_ACCT_TYPE_LOCKED);
		firstLoginUser = firstLoginUser != null ? firstLoginUser
				: mockUser.getMockUser(MockUser.MOCK_USER_ACCT_TYPE_FIRST_LOGIN);
		pwdExpiredUser = pwdExpiredUser != null ? pwdExpiredUser
				: mockUser.getMockUser(MockUser.MOCK_USER_ACCT_TYPE_PWD_EXPIRED);
		noSuchUser = noSuchUser != null ? noSuchUser : mockUser.getMockUser(MockUser.MOCK_USER_ACCT_NO_SUCH);
		pwdErrUser = pwdErrUser != null ? pwdErrUser : mockUser.getMockUser(MockUser.MOCK_USER_ACCT_PWD_ERR);
		newUser = newUser != null ? newUser : mockUser.getMockUser(MockUser.MOCK_USER_ACCT_NEW);
		userList.add(normalUser);
		userList.add(lockedUser);
		userList.add(firstLoginUser);
		userList.add(pwdExpiredUser);
		userList.add(noSuchUser);
		userList.add(pwdErrUser);
		userList.add(newUser);
		log.info("给所有测试用户进行登录操作,设置session");
		// 模拟登录,设置session
		this.setLoginSession4AllUser(userList);
	}

	/**
	 * 前置操作
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		log.info("在每个测试方法执行前执行");
		// 方式1：@Autowired注解装配UserController
		// mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		// 方式2：@Autowired注解装配WebApplicationContext
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		init();

	}

	// -----------------------------------------------------------------登录接口测试------------------------------------------------------
	/**
	 * 测试登录成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginSuccess() throws Exception {
		log.info("测试登录成功");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		params.add(Const.PWD, normalUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_SUCCESS, true, new MockHttpSession());
	}

	/**
	 * 测试登录失败,无传入参数
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailWithoutParams() throws Exception {
		log.info("测试登录失败,无传入参数");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_MSG, Const.MSG_NO_PARAMS,
				new MockHttpSession());
	}

	/**
	 * 测试登录失败, 账号已锁定
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailUserLockin() throws Exception {
		log.info("测试登录失败,账号锁定");
		params.clear();
		params.add(Const.USERID, lockedUser.getUserId());
		params.add(Const.PWD, lockedUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_DATA, Const.USER_IS_LOCKEDIN,
				new MockHttpSession());
	}

	/**
	 * 测试登录失败,首次登录
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailFirstLogin() throws Exception {
		log.info("测试登录失败,首次登录");
		params.clear();
		params.add(Const.USERID, firstLoginUser.getUserId());
		params.add(Const.PWD, firstLoginUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_DATA,
				Const.USER_FIRST_LOGIN_PLEASE_MODIFY_PWD, new MockHttpSession());
	}

	/**
	 * 测试登录失败,密码过期
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailPwdExpired() throws Exception {
		log.info("测试登录失败,密码过期");
		params.clear();
		params.add(Const.USERID, pwdExpiredUser.getUserId());
		params.add(Const.PWD, pwdExpiredUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_DATA, Const.USER_PWD_EXPIRED,
				new MockHttpSession());
	}

	/**
	 * 测试登录失败,用户不存在
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFailNoSuchUser() throws Exception {
		log.info("测试登录失败,用户不存在");
		params.clear();
		params.add(Const.USERID, noSuchUser.getUserId());
		params.add(Const.PWD, noSuchUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_DATA, Const.USER_WITH_USERID_NOT_EXISTS,
				new MockHttpSession());
	}

	@Test
	public void testLoginFailPwdErr() throws Exception {
		log.info("测试登录失败,密码错误");
		params.clear();
		params.add(Const.USERID, pwdErrUser.getUserId());
		params.add(Const.PWD, pwdErrUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_LOGIN, params, Const.JSON_PATH_DATA, Const.USER_WRONG_PWD,
				new MockHttpSession());
	}

	// --------------------------------------------------注销接口测试-----------------------------------------------
	/**
	 * 测试注销成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogoutSuccess() throws Exception {
		log.info("测试注销成功");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.POST, Const.URL_LOGOUT, params, Const.JSON_PATH_SUCCESS, true, normalUser.getSession());
	}
	// -----------------------------------------------增加接口测试---------------------------------------

	/**
	 * 测试增加成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddUserSuccess() throws Exception {
		log.info("测试增加成功");
		params.clear();
		params.add(Const.USERID, newUser.getUserId());
		params.add(Const.PWD, newUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_ADD_USER, params, Const.JSON_PATH_SUCCESS, true,
				normalUser.getSession());
	}

	/**
	 * 测试增加失败,无session
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddUserFailWithoutSession() throws Exception {
		log.info("测试增加失败,无session");
		params.clear();
		params.add(Const.USERID, newUser.getUserId());
		params.add(Const.PWD, newUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_ADD_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_SESSION,
				new MockHttpSession());
	}

	/**
	 * 测试增加失败,无传入参数
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddUserFailWithoutParams() throws Exception {
		log.info("测试增加失败,无传入参数");
		params.clear();
		this.perform(HttpMethod.POST, Const.URL_ADD_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_PARAMS,
				normalUser.getSession());
	}

	/**
	 * 测试增加失败, 用户已存在
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddUserFailUserExists() throws Exception {
		log.info("测试增加失败, 用户已存在");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		params.add(Const.PWD, normalUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_ADD_USER, params, Const.JSON_PATH_MSG, Const.MSG_USER_EXISTS_MINUS5,
				normalUser.getSession());
	}

	// ----------------------------------------------删除接口测试-----------------------------------
	/**
	 * 测试删除成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelUserSuccess() throws Exception {
		log.info("测试删除成功");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.POST, Const.URL_DEL_USER, params, Const.JSON_PATH_SUCCESS, true,
				normalUser.getSession());
	}

	/**
	 * 测试删除失败,无session
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelUserFailWithoutSession() throws Exception {
		log.info("测试删除失败, 无session");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.POST, Const.URL_DEL_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_SESSION,
				new MockHttpSession());
	}

	/**
	 * 测试删除失败,无传入参数
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelUserFailWithoutParams() throws Exception {
		log.info("测试删除失败, 无传入参数");
		params.clear();
		this.perform(HttpMethod.POST, Const.URL_DEL_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_PARAMS,
				normalUser.getSession());
	}
	// ---------------------------------------修改接口测试---------------------------------------------

	/**
	 * 测试修改成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserSuccess() throws Exception {
		log.info("测试修改成功");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		params.add(Const.PWD, normalUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_SUCCESS, true,
				normalUser.getSession());
	}

	/**
	 * 测试修改失败,无session
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserFailWithoutSession() throws Exception {
		log.info("测试修改失败,无session");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_SESSION,
				new MockHttpSession());
	}

	/**
	 * 测试修改失败,无传入参数
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserFailWithoutParams() throws Exception {
		log.info("测试修改失败,无传入参数");
		params.clear();
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_PARAMS,
				normalUser.getSession());
	}

	/**
	 * 测试修改失败,用户不存在
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUserFailNoSuchUser() throws Exception {
		log.info("测试修改失败,用户不存在");
		params.clear();
		params.add(Const.USERID, noSuchUser.getUserId());
		params.add(Const.PWD, noSuchUser.getPwd());
		this.perform(HttpMethod.POST, Const.URL_MODI_USER, params, Const.JSON_PATH_MSG,
				Const.MSG_USER_NOT_EXISTS_MINUS4, normalUser.getSession());
	}

	// -----------------------------------查询接口测试--------------------------------
	/**
	 * 测试查询成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueryUserSuccess() throws Exception {
		log.info("测试查询成功");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.GET, Const.URL_QUERY_USER, params, Const.JSON_PATH_SUCCESS, true,
				normalUser.getSession());
	}

	/**
	 * 测试查询失败,无session
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueryUserFailWithoutSession() throws Exception {
		log.info("测试查询失败,无session");
		params.clear();
		params.add(Const.USERID, normalUser.getUserId());
		this.perform(HttpMethod.GET, Const.URL_QUERY_USER, params, Const.JSON_PATH_MSG, Const.MSG_NO_SESSION,
				new MockHttpSession());
	}

	// -----------------------------------私有方法------------------------------------

	/**
	 * 设置登陸session
	 * 
	 * @param user
	 *            测试用户
	 * @throws Exception
	 */
	private void setLoginSession(TestUser user) throws Exception {

		params.clear();
		params.add(Const.USERID, user.getUser().getUserId());
		params.add(Const.PWD, user.getUser().getPwd());
		// 模拟登录,设置session
		ResultActions resultActions = this.performReturnResultActions(HttpMethod.POST, Const.URL_LOGIN, params,
				new MockHttpSession());
		// 获取接口返回的结果
		MvcResult result = resultActions.andReturn();
		// 设置session
		HttpSession httpSession = result.getRequest().getSession();
		if (httpSession != null) {
			user.setSession((MockHttpSession) httpSession);
		} else {
			user.setSession(new MockHttpSession());
		}
		log.info("给测试用户" + user.getUserId() + "进行登录操作,设置session, sesession中的内容:"
				+ user.getSession().getAttribute(Const.LOGIN_USER));
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
	 * @param session
	 *            session
	 * @throws Exception
	 */
	private void perform(HttpMethod method, String url, MultiValueMap<String, String> params, String jsonPath,
			Object value, MockHttpSession session) throws Exception {
		ResultActions resultActions = this.performReturnResultActions(method, url, params, session);
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
	private ResultActions performReturnResultActions(HttpMethod method, String url,
			MultiValueMap<String, String> params, MockHttpSession session) throws Exception {
		MockHttpServletRequestBuilder builder = null;
		if (method == HttpMethod.GET) {
			builder = MockMvcRequestBuilders.get(url);
		} else if (method == HttpMethod.POST) {
			builder = MockMvcRequestBuilders.post(url);
		}
		ResultActions resultActions = mockMvc.perform(builder.params(params).session(session))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		if (doPrint) {
			resultActions.andDo(MockMvcResultHandlers.print());
		}
		return resultActions;
	}

	/**
	 * 给所有测试用户进行登录操作,设置session
	 * 
	 * @param userList
	 * 
	 * @throws Exception
	 */
	private void setLoginSession4AllUser(List<TestUser> userList) throws Exception {
		for (TestUser user : userList) {
			this.setLoginSession(user);
		}
	}

}
