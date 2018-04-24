package com.chenqiang.study.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chenqiang.study.dao.UserDao;
import com.chenqiang.study.entity.User;
import com.chenqiang.study.service.UserService;
import com.chenqiang.study.util.Const;
import com.chenqiang.study.util.SecurityUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * 用户业务实现层
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
@Service
public class UserServiceImpl implements UserService {
	/** 用户DAO层 */
	@Autowired
	private UserDao userDao;
	/**
	 * 自定义密码错误次数
	 */
	@Value("${cust.pwd.errCout}")
	private int custPwdErrCout;
	/**
	 * 自定义密码有效期长度
	 */
	@Value("${cust.pwd.expiryDate}")
	private int custPWdExpiryDate;
	/**
	 * 自定义密码最大记录数
	 */
	@Value("${cust.pwd.recCout}")
	private int custPwdRecCout;
	/**
	 * 私钥
	 */
	@Value("${cust.rsa.privateKey}")
	private String privateKey;

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
	@Override
	public User login(String userId, String pwd) {
		return this.userDao.login(userId, pwd);
	}

	/**
	 * 添加用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @param name
	 *            姓名
	 * @param age
	 *            年龄
	 * @return
	 * @throws Exception
	 */
	@Override
	public int addUser(String userId, String pwd, String name, Integer age) throws Exception {
		// 根据userId查询用户信息
		User user = this.userDao.queryUserByUserId(userId);
		if (user == null) {
			// 密码rsa解密后使用base64加密
			String pwdDec = SecurityUtil.decByRSA(pwd, privateKey);
			String pwdMD5 = DigestUtil.md5Hex(pwdDec);
			User param = new User();
			param.setUserId(userId);
			param.setName(name);
			param.setAge(age);
			param.setPwd(pwdMD5);
			param.setPwdRec(pwdMD5);
			// 获取密码有效日期
			Date pwdExpiredDate = DateUtil.offsetDay(new Date(), custPWdExpiryDate);
			param.setPwdExpiredDate(pwdExpiredDate);
			return this.userDao.addUser(param);
		} else {
			return Const.USER_WITH_USERID_ALREADY_EXISTS;
		}
	}

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
	@Override
	public int delUser(String userId) {
		return this.userDao.delUser(userId);
	}

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
	 * @param age
	 *            年龄
	 * @param name
	 *            姓名
	 * @return
	 * @throws Exception
	 */
	@Override
	public int modiUser(String userId, String pwd, String name, Integer age) throws Exception {

		// 根据userId查询用户信息
		User user = this.userDao.queryUserByUserId(userId);
		if (user == null) {
			return Const.USER_WITH_USERID_NOT_EXISTS;
		}
		// 定义修改参数
		User param = new User();
		param.setUserId(userId);
		param.setName(name);
		param.setAge(age);
		if (StrUtil.isNotBlank(pwd)) {
			// 密码rsa解密后md5加密
			String pwdDec = SecurityUtil.decByRSA(pwd, privateKey);
			String pwdMD5 = DigestUtil.md5Hex(pwdDec);
			param.setPwd(pwdMD5);
			// 定义修改之后的密码记录
			String pwdRecAfter = null;
			// 获取修改之前的密码记录
			String pwdRec = user.getPwdRec();
			List<String> pwdRecList = new ArrayList<String>(Arrays.asList(pwdRec.split(",")));
			if (pwdRecList.size() < custPwdRecCout) {
				pwdRecAfter = pwdRec + "," + pwdMD5;
			} else {
				// 移除第一个
				pwdRecList.remove(0);
				// 将当前密码加入到list最后
				pwdRecList.add(pwdMD5);
				// 将list转为字符串,list的每个元素之间用逗号分隔
				pwdRecAfter = StrUtil.join(",", pwdRecList);
			}
			param.setPwdRec(pwdRecAfter);
			// 添加密码修改记录
			this.userDao.addPwdRec(userId, pwdRec);
		}
		Date pwdExpiredDate = DateUtil.offsetDay(new Date(), custPWdExpiryDate);
		param.setPwdExpiredDate(pwdExpiredDate);
		return this.userDao.modiUserInfoByUserId(param);

	}

	/**
	 * 查询用户
	 * 
	 * @author qchen
	 * @date 2018-4-13
	 * @param userId
	 *            用户id(可选)
	 * @return
	 */
	@Override
	public List<User> queryUser(String userId) {
		return this.userDao.queryUser(userId);
	}

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
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getLoginMap(String userId, String pwd) throws Exception {
		// 定义一个map,存放返回的信息
		Map<String, Object> map = new HashMap<String, Object>();
		// 定义一个user实体,用于存放参数,进行数据库操作
		User param = new User(userId, null, null, null, null, null);
		int flag = 0;
		// 根据userId查询用户信息
		User user = this.userDao.queryUserByUserId(userId);
		// 如果能查找到指定用户id 的用户信息
		if (user != null) {
			// 获取账号锁定标识
			int isLock = user.getIsLock();
			// 如果账号未锁定
			if (isLock == Const.USER_ACCOUNT_STATUS_NOT_LOCKED) {
				// 处理前台传来的密码(rsa解密)
				String pwdDec = SecurityUtil.decByRSA(pwd, privateKey);
				// 如果数据库中的密码和用户输入的密码相同,说明该用户的用户id和密码正确无误
				// 获取数据库中存储的密码
				String pwdOfUser = user.getPwd();
				if (DigestUtil.md5Hex(pwdDec).equals(pwdOfUser)) {
					// 获取该账号的密码有效期限
					Date pwdExpiredDate = user.getPwdExpiredDate();
					// 获取用户密码是否过期标识
					boolean isPwdExpired = new Date().after(pwdExpiredDate);
					// 如果过期,转密码修改页面
					if (isPwdExpired) {
						flag = Const.USER_PWD_EXPIRED;
						map.put(Const.USER_FROM_DB, user);
					} else {
						// 密码未过期,判断用户是否是首次登录
						// 获取创建日期,和更新日期,比较这两个日期是否相同
						// 如果这两个日期相同,说明该用户此次是首次登录,需要修改密码
						Date createTime = user.getCreateTime();
						Date updateTime = user.getUpdateTime();
						if (createTime.compareTo(updateTime) == 0) {
							flag = Const.USER_FIRST_LOGIN_PLEASE_MODIFY_PWD;
							map.put(Const.USER_FROM_DB, user);
						} else {
							// 更新用户最近一次登陆日期
							param.setLastLoginDate(new Date());
							flag = this.userDao.modiUserInfoByUserId(param);
							if (flag > 0) {
								flag = Const.USER_LOGIN_SUCCESS;
								map.put(Const.USER_FROM_DB, user);
							}
						}
					}

				} else {
					// 如果密码不同,说明密码输入错误
					// 获取用户此次密码错误总次数
					int pwdErrCout = user.getPwdErrCout() + 1;
					if (pwdErrCout < custPwdErrCout) {
						// 更新密码错误次数
						param.setPwdErrCout(pwdErrCout);
						flag = this.userDao.modiUserInfoByUserId(param);
						if (flag > 0) {
							flag = Const.USER_WRONG_PWD;
						}
					} else {
						// 用户账号锁定
						param.setIsLock(Const.USER_ACCOUNT_STATUS_LOCKED);
						param.setPwdErrCout(pwdErrCout);
						// 更新账号锁定标识
						flag = this.userDao.modiUserInfoByUserId(param);
						if (flag > 0) {
							flag = Const.USER_IS_LOCKEDIN;
						}
					}

				}
			} else {
				flag = Const.USER_IS_LOCKEDIN;
			}

		} else {
			flag = Const.USER_WITH_USERID_NOT_EXISTS;
		}
		map.put("flag", flag);
		return map;
	}

}
