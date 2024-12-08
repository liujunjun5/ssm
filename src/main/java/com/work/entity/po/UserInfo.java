package com.work.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:用户信息
 * @date:2024-12-02
 * @author: liujun
 */
public class UserInfo implements Serializable {
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 0:女 1:男 2:未知
	 */
	private Integer sex;

	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 个人简介
	 */
	private String personIntroduction;

	/**
	 * 加入时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	/**
	 * 0:禁用 1:正常
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 公告
	 */
	private String noticeInfo;

	/**
	 * 当前金额数
	 */
	private BigDecimal currentCoinCount;

	/**
	 * 主题
	 */
	private Integer theme;


	/**ljz
	 * 用于回显的用户数据转用户数据更新类
	 */
	public UserInfo(ClaimsOfUserInfo claimsOfUserInfo ){

		this.nickName = claimsOfUserInfo.getNickName();
		this.avatar = claimsOfUserInfo.getAvatar();
		this.sex = claimsOfUserInfo.getSex();
		this.birthday = claimsOfUserInfo.getBirthday();
		this.personIntroduction = claimsOfUserInfo.getPersonIntroduction();
		this.noticeInfo = claimsOfUserInfo.getNoticeInfo();

	}

	/**ljz
	 * 注册对象初始化
	 */
	public UserInfo(String nickName, String email, String registerPassword) {

		this.nickName = nickName;
		this.email = email;
		this.password = registerPassword;

	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setPersonIntroduction(String personIntroduction) {
		this.personIntroduction = personIntroduction;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public void setCurrentCoinCount(BigDecimal currentCoinCount) {
		this.currentCoinCount = currentCoinCount;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getSex() {
		return this.sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public String getPersonIntroduction() {
		return this.personIntroduction;
	}

	public Date getJoinTime() {
		return this.joinTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getNoticeInfo() {
		return this.noticeInfo;
	}

	public BigDecimal getCurrentCoinCount() {
		return this.currentCoinCount;
	}

	public Integer getTheme() {
		return this.theme;
	}

	@Override
	public String toString() {
		return "用户id:" + (userId == null ? "null" : userId) + ",昵称:" + (nickName == null ? "null" : nickName) + ",头像:" + (avatar == null ? "null" : avatar) + ",邮箱:" + (email == null ? "null" : email) + ",密码:" + (password == null ? "null" : password) + ",0:女 1:男 2:未知:" + (sex == null ? "null" : sex) + ",出生日期:" + (birthday == null ? "null" : birthday) + ",个人简介:" + (personIntroduction == null ? "null" : personIntroduction) + ",加入时间:" + (joinTime == null ? "null" : joinTime) + ",最后登录时间:" + (lastLoginTime == null ? "null" : lastLoginTime) + ",最后登录IP:" + (lastLoginIp == null ? "null" : lastLoginIp) + ",0:禁用 1:正常:" + (status == null ? "null" : status) + ",公告:" + (noticeInfo == null ? "null" : noticeInfo) + ",当前金额数:" + (currentCoinCount == null ? "null" : currentCoinCount) + ",主题:" + (theme == null ? "null" : theme);
	}
}