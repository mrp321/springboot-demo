package com.chenqiang.study.service.impl;

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

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
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
	@Value("${cust.pwd.errCout}")
	private int custPwdErrCout;
	@Value("${cust.pwd.expiryDate}")
	private int custPWdExpiryDate;

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
	 * @return
	 */
	@Override
	public int addUser(String userId, String pwd) {
		List<User> userList = this.queryUser(userId);
		if (userList == null || userList.size() == 0) {
			return this.userDao.addUser(userId, DigestUtil.md5Hex(pwd));
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
	 * @return
	 */
	@Override
	public int modiUser(String userId, String pwd) {
		return this.userDao.modiUser(userId, pwd);

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
	 */
	@Override
	public Map<String, Object> getLoginMap(String userId, String pwd) {
		// 定义一个map,存放返回的信息
		Map<String, Object> map = new HashMap<String, Object>();
		// 定义一个user实体,用于存放参数,进行数据库操作
		User param = new User(userId, null, null, null, null, null);
		int flag = 0;
		// 根据userId查询用户信息
		User user = this.userDao.queryUserByUserId(userId);
		if (user != null) {
			// 根据userId可以查询到该用户信息,则判断密码是否相同
			String pwdOfUser = user.getPwd();
			// 如果密码相同,说明是成功登录
			// 如果密码不同,说明密码输入错误
			if (DigestUtil.md5Hex(pwd).equals(pwdOfUser)) {
				// 如果密码相同,查询用户是否被锁定
				// 获取密码错误次数
				int pwdErrCout = user.getPwdErrCout();
				// 密码未锁定,继续进行后续操作;已锁定,返回错误信息
				if (pwdErrCout < custPwdErrCout) {
					// 获取该账号的创建日期
					Date createTime = user.getCreateTime();
					// 定义密码过期标志
					boolean isPwdExpired = this.isPwdExpired(createTime, new Date(), DateField.DAY_OF_YEAR, custPWdExpiryDate);
					// 如果过期,转密码修改页面
					if (isPwdExpired) {
						flag = Const.USER_PWD_EXPIRED;
					} else {
						// 密码未过期,判断用户是否是首次登录
						Date lastLoginDate = user.getLastLoginDate();
						// 如果最近用户登录日期为null,说明该用户此次是首次登录
						if (lastLoginDate == null) {
							flag = Const.USER_FIRST_LOGIN_PLEASE_MODIFY_PWD;
						} else {
							flag = Const.USER_LOGIN_SUCCESS;
							map.put(Const.USER_FROM_DB, user);
						}
					}
				} else {
					flag = Const.USER_IS_LOCKEDIN;
				}

			} else {
				// 获取密码错误次数
				int pwdErrCout = user.getPwdErrCout();
				if (pwdErrCout < custPwdErrCout) {
					// 密码错误次数+1
					param.setPwdErrCout(pwdErrCout + 1);
				flag = this.userDao.modiUserInfoByUserId(param);
				} else {
					// 用户账号锁定
					param.setIsLock(1);
					flag = this.userDao.modiUserInfoByUserId(param);
					if (flag > 0) {
						flag = Const.USER_IS_LOCKEDIN;
					}
				}
			}
		} else {
			flag = Const.USER_WITH_USERID_NOT_EXISTS;
		}
		map.put("flag", flag);
		return map;
	}

	/**
	 * 判断指定日期经过一段时间后的日期和需要被比较的日期相比,被比较的日期是否已经过期
	 * 
	 * @author qchen
	 * @date 20178-4-17
	 * @param startDate
	 *            开始日期
	 * @param compareDate
	 *            被比较的日期
	 * @param dateField
	 *            时间间隔格式
	 * @param custPWdExpiryDate
	 *            时间间隔
	 * @return
	 */
	private boolean isPwdExpired(Date startDate, Date compareDate, DateField dateField, int custPWdExpiryDate) {
		boolean isPwdExpired = false;
		// startDate经过指定时间后的日期
		final Date endDate = DateUtil.offset(startDate, dateField, custPWdExpiryDate);
		// 如果endDate 小于被比较的日期,则说明已经过期
		if (endDate.before(compareDate)) {
			isPwdExpired = true;
		}
		return isPwdExpired;
	}

}
