package com.chenqiang.study.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.chenqiang.study.entity.User;
import com.chenqiang.study.provider.UserProvider;

/**
 * 用户业务DAO层
 * 
 * @author QChen
 * @date 2018-4-13
 */
public interface UserDao {

	/**
	 * 登陆
	 * 
	 * @author QChen
	 * @date 2018-4-11
	 * 
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return 用户信息
	 */
	@Select("SELECT userId, pwd FROM user WHERE userId = #{userId} AND pwd = #{pwd}")
	public User login(@Param("userId") String userId, @Param("pwd") String pwd);

	/**
	 * 添加用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return
	 */
	@Insert("INSERT INTO user(userId, name, sex, age, pwd, pwdRec, pwdErrCout, pwdExpiredDate, isLock, createTime, createUser, updateTime, updateUser, version) VALUES(#{userId},  #{name}, #{sex}, #{age}, #{pwd}, #{pwd}, 0, #{pwdExpiredDate}, 0, NOW(), #{userId}, NOW(), #{userId}, 0)")
	public int addUser(User user);

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
	@Delete("DELETE FROM user WHERE userId = #{userId}")
	int delUser(@Param("userId") String userId);

	/**
	 * 修改用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * 
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return
	 */
	@Update("UPDATE user SET pwd = #{pwd}, updateUser = #{userId}, updateTime = NOW(), version = version + 1 WHERE userId = #{userId}")
	int modiUser(@Param("userId") String userId, @Param("pwd") String pwd);

	/**
	 * 查询用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param userId
	 *            用户id(可选)
	 * @return
	 */
	@SelectProvider(type = UserProvider.class, method = "queryUser")
	List<User> queryUser(String userId);

	/**
	 * 根据用户id查询用户
	 * 
	 * @author qchen
	 * @date 2018-4-17
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Select("SELECT userId, pwd, pwdRec, pwdErrCout, pwdExpiredDate, LastLoginDate, isLock, createTime, updateTime FROM user WHERE userId = #{userId}")
	User queryUserByUserId(String userId);

	/**
	 * 根据用户id修改用户信息
	 * 
	 * @author qchen
	 * @date 2018-4-17
	 * @param user
	 *            包含所需参数的用户实体
	 * @return
	 */
	public int modiUserInfoByUserId(User user);

	/**
	 * 添加密码修改记录
	 * 
	 * @author qchen
	 * @date 2018-4-24
	 * @param userId
	 *            用户id
	 * @param pwdRec
	 *            修改前密码
	 * @return
	 */
	@Insert("INSERT INTO pwd_rec(userId, pwd, createTime, createUser, updateTime, updateUser, version) VALUES(#{userId}, #{pwdRec}, NOW(), #{userId}, NOW(), #{userId}, 0)")
	public int addPwdRec(String userId, String pwdRec);

}
