package com.chenqiang.study.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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

	/**
	 * 前置操作
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// UserController userController = new UserController();
		// // 由于用mock测试，这些controller里面的注解类都是空对象，需要通过这种反射的方式写到controller里面去
		// ReflectionTestUtils.setField(userController, "userService", userService);
		// mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		// 创建MockMvc实例
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 测试登录
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		String userId = "test2";
		String pwd = "K1/GHPRBIKhzhBLZNQeKu4L3WQJTLdh5xYQpn6w/l6bfDDo+zHeJ7EKrD1f1GD5wx/70VMFJbFAj9LsYG6uG05eFaFNuDaqwio63/Nx2i9sVXf0prFMMBZyX2tpmGVVtEW+OkwyAEeB8MKucuNloGNGW09ghBBD6edGZa+ve3fE=";
		mockMvc.perform(MockMvcRequestBuilders.post("/user/login").param("userId", userId).param("pwd", pwd)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
	}

	/**
	 * 测试注销
	 * 
	 * @throws Exception
	 */
	// @Test
	public void testLogout() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/logout")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * 测试删除
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelUser() throws Exception {
		String userId = "root";
		mockMvc.perform(MockMvcRequestBuilders.post("/user/delUser").param("userId", userId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
	}

	/**
	 * 测试修改
	 * 
	 * @throws Exception
	 */
	@Test
	public void testModiUser() throws Exception {
		String userId = "test2";
		String pwd = "K1/GHPRBIKhzhBLZNQeKu4L3WQJTLdh5xYQpn6w/l6bfDDo+zHeJ7EKrD1f1GD5wx/70VMFJbFAj9LsYG6uG05eFaFNuDaqwio63/Nx2i9sVXf0prFMMBZyX2tpmGVVtEW+OkwyAEeB8MKucuNloGNGW09ghBBD6edGZa+ve3fE=";

		mockMvc.perform(MockMvcRequestBuilders.post("/user/modiUser").param("userId", userId).param("pwd", pwd))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
	}

	/**
	 * 测试查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueryUser() throws Exception {
		String userId = "test2";

		mockMvc.perform(MockMvcRequestBuilders.post("/user/modiUser").param("userId", userId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
	}

}
