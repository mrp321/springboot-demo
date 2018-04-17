package com.chenqiang.study.entity;

import java.util.Date;

/**
 * 用户实体
 * 
 * @author QChen
 * @date 2018-4-11
 *
 */
public class User {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * 
	 * 性别
	 */
	private Integer sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 密码记录
	 */
	private String pwdRec;
	/**
	 * 密码错误次数
	 */
	private Integer pwdErrCout;
	/**
	 * 最后一次登录日期
	 */
	private Date lastLoginDate;
	/**
	 * 是否锁定
	 */
	private Integer isLock;
	/**
	 * 用户创建日期
	 */
	private Date createTime;

	public User() {

	}

	/**
	 * 含参构造器
	 * 
	 * @author QChen
	 * @date 2018-4-17
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            密码
	 * @param pwdRec
	 *            密码记录
	 * @param pwdErrCout
	 *            密码错误次数
	 * @param lastLoginDate
	 *            最后一次登录时间
	 * @param isLock
	 *            是否锁定
	 */
	public User(String userId, String pwd, String pwdRec, Integer pwdErrCout, Date lastLoginDate, Integer isLock) {
		super();
		this.userId = userId;
		this.pwd = pwd;
		this.pwdRec = pwdRec;
		this.pwdErrCout = pwdErrCout;
		this.lastLoginDate = lastLoginDate;
		this.isLock = isLock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPwdRec() {
		return pwdRec;
	}

	public void setPwdRec(String pwdRec) {
		this.pwdRec = pwdRec;
	}

	public Integer getPwdErrCout() {
		return pwdErrCout;
	}

	public void setPwdErrCout(Integer pwdErrCout) {
		this.pwdErrCout = pwdErrCout;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", pwd=" + pwd + ", sex=" + sex + ", age=" + age
				+ ", pwdRec=" + pwdRec + ", pwdErrCout=" + pwdErrCout + ", lastLoginDate=" + lastLoginDate + ", isLock="
				+ isLock + ", createTime=" + createTime + "]";
	}

}
