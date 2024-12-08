package com.work.entity.query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:用户信息查询对象
 * @date:2024-12-02
 * @author: liujun
 */
public class UserInfoQuery extends BaseQuery{
	/**
	 * 用户id
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 头像
	 */
	private String avatar;

	private String avatarFuzzy;

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 0:女 1:男 2:未知
	 */
	private Integer sex;

	/**
	 * 出生日期
	 */
	private String birthday;

	private String birthdayFuzzy;

	/**
	 * 个人简介
	 */
	private String personIntroduction;

	private String personIntroductionFuzzy;

	/**
	 * 加入时间
	 */
	private Date joinTime;

	private String joinTimeStart;

	private String joinTimeEnd;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	private String lastLoginIpFuzzy;

	/**
	 * 0:禁用 1:正常
	 */
	private Integer status;

	/**
	 * 公告
	 */
	private String noticeInfo;

	private String noticeInfoFuzzy;

	/**
	 * 当前金额数
	 */
	private BigDecimal currentCoinCount;

	/**
	 * 主题
	 */
	private Integer theme;




	/**ljz
	 * 登录查询对象初始化
	 */
	public UserInfoQuery (String email, String passwordMd5){

		this.email = email;
		this.password = passwordMd5;

	}



	public UserInfoQuery() {

	}




    public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setAvatarFuzzy(String avatarFuzzy) {
		this.avatarFuzzy = avatarFuzzy;
	}

	public String getAvatarFuzzy() {
		return this.avatarFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy() {
		return this.emailFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy) {
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy() {
		return this.passwordFuzzy;
	}

	public void setBirthdayFuzzy(String birthdayFuzzy) {
		this.birthdayFuzzy = birthdayFuzzy;
	}

	public String getBirthdayFuzzy() {
		return this.birthdayFuzzy;
	}

	public void setPersonIntroductionFuzzy(String personIntroductionFuzzy) {
		this.personIntroductionFuzzy = personIntroductionFuzzy;
	}

	public String getPersonIntroductionFuzzy() {
		return this.personIntroductionFuzzy;
	}

	public void setJoinTimeStart(String joinTimeStart) {
		this.joinTimeStart = joinTimeStart;
	}

	public String getJoinTimeStart() {
		return this.joinTimeStart;
	}

	public void setJoinTimeEnd(String joinTimeEnd) {
		this.joinTimeEnd = joinTimeEnd;
	}

	public String getJoinTimeEnd() {
		return this.joinTimeEnd;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart() {
		return this.lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd() {
		return this.lastLoginTimeEnd;
	}

	public void setLastLoginIpFuzzy(String lastLoginIpFuzzy) {
		this.lastLoginIpFuzzy = lastLoginIpFuzzy;
	}

	public String getLastLoginIpFuzzy() {
		return this.lastLoginIpFuzzy;
	}

	public void setNoticeInfoFuzzy(String noticeInfoFuzzy) {
		this.noticeInfoFuzzy = noticeInfoFuzzy;
	}

	public String getNoticeInfoFuzzy() {
		return this.noticeInfoFuzzy;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setPersonIntroduction(String personIntroduction) {
		this.personIntroduction = personIntroduction;
	}

	public String getPersonIntroduction() {
		return this.personIntroduction;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Date getJoinTime() {
		return this.joinTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public String getNoticeInfo() {
		return this.noticeInfo;
	}

	public void setCurrentCoinCount(BigDecimal currentCoinCount) {
		this.currentCoinCount = currentCoinCount;
	}

	public BigDecimal getCurrentCoinCount() {
		return this.currentCoinCount;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	public Integer getTheme() {
		return this.theme;
	}

}