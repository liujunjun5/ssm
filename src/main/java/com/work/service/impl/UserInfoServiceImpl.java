package com.work.service.impl;


import com.alibaba.fastjson.JSON;
import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.UserInfo;
import com.work.entity.query.SimplePage;
import com.work.entity.query.UserInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.exception.BusinessException;
import com.work.mappers.RedisDataMapper;
import com.work.mappers.UserInfoMappers;
import com.work.service.UserInfoService;
import com.work.utils.JwtUtils;
import com.work.utils.StringTools;

import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description:用户信息Service
 * @date:2024-12-02
 * @author: liujun
 */
@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMappers<UserInfo, UserInfoQuery> userInfoMappers;

	@Autowired
	private RedisDataMapper redisDataMapper ;

	/**
	 * 根据条件查询列表
	 */
	public List<UserInfo> findListByParam(UserInfoQuery query) {
		return this.userInfoMappers.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(UserInfoQuery query) {
		return this.userInfoMappers.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<UserInfo> findByPage(UserInfoQuery query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserInfo> list = this.findListByParam(query);
		PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(UserInfo bean) {
		return this.userInfoMappers.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userInfoMappers.insertBatch(listBean);
	}

	/**
	 * 批量新增或更新
	 */
	public Integer addOrUpdateBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userInfoMappers.insertOrUpdateBatch(listBean);
	}


	/**
	 * 根据UserId查询
	 */
	public UserInfo getByUserId(String userId) {
		return this.userInfoMappers.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	public Integer updateByUserId(UserInfo bean, String userId) {return this.userInfoMappers.updateByUserId(bean, userId);}

	/**
	 * 根据UserId删除
	 */
	public Integer deleteByUserId(String userId) {
		return this.userInfoMappers.deleteByUserId(userId);
	}


	/**
	 * 根据Email查询
	 */
	public UserInfo getByEmail(String email) {
		return this.userInfoMappers.selectByEmail(email);
	}

	/**
	 * 根据Email更新
	 */
	public Integer updateByEmail(UserInfo bean, String email) {
		return this.userInfoMappers.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	public Integer deleteByEmail(String email) {
		return this.userInfoMappers.deleteByEmail(email);
	}


	/**
	 * 根据NickName查询
	 */
	public UserInfo getByNickName(String nickName) {
		return this.userInfoMappers.selectByNickName(nickName);
	}

	/**
	 * 根据NickName更新
	 */
	public Integer updateByNickName(UserInfo bean, String nickName) {
		return this.userInfoMappers.updateByNickName(bean, nickName);
	}

	/**
	 * 根据NickName删除
	 */
	public Integer deleteByNickName(String nickName) {
		return this.userInfoMappers.deleteByNickName(nickName);
	}

	/**
	 * ljz
	 * 登录查询操作
	 */
	public String findOneByParam(UserInfoQuery userInfoQuery) throws BusinessException {

		UserInfo userInfo = this.userInfoMappers.selectByEmail(userInfoQuery.getEmail());

		//检验账号是否存在
		if(userInfo == null){
			throw new BusinessException(602,"账号不存在");
		}
		//密码校验
		if(!userInfo.getPassword().equals(userInfoQuery.getPassword())){
			throw new BusinessException(602,"密码错误");
		}
		//检验账号是否被封禁
		if(userInfo.getStatus() == 0){
			throw new BusinessException(602,"账号被封禁");
		}
		//登录成功更新最后登录时间
		userInfo.setLastLoginTime(new Date());
		//登录成功封装要展示的用户信息
		ClaimsOfUserInfo claimsOfUserInfo = new ClaimsOfUserInfo(userInfo.getUserId(),userInfo.getNickName(),userInfo.getAvatar(),userInfo.getSex(),userInfo.getBirthday(),userInfo.getPersonIntroduction(),userInfo.getNoticeInfo());

		ClaimsOfUserInfo Md5Claims = claimsOfUserInfo.encryptOfMd5(claimsOfUserInfo);//将部分数据做加密操作，生成Jwt令牌
		String jwt = JwtUtils.generateJwt(Md5Claims);
		//将用户信息存入Redis
		redisDataMapper.setData("user:"+jwt,claimsOfUserInfo,60*60*3);
		return jwt;
	}

	/**
	 * ljz
	 * 注册
	 */
	@Override
	public String register(UserInfo userInfo) throws BusinessException {
		//检查是否有相同邮箱账号
		if(userInfoMappers.selectByEmail(userInfo.getEmail()) != null){
			throw new BusinessException(602,"该邮箱已注册");
		}
		//昵称默认赋值
		if(!StringUtils.hasLength(userInfo.getNickName())){
			userInfo.setNickName("用户"+userInfoMappers.selectCount(new UserInfoQuery()).toString()) ;
		}
		//创建新用户
		userInfo.setUserId(StringTools.getRandomNumber(Constants.LENGTH_10));
		userInfo.setJoinTime(new Date());
		userInfo.setLastLoginTime(new Date());
		userInfo.setCurrentCoinCount(BigDecimal.ZERO);
		//新账号录入数据库
		this.userInfoMappers.insert(userInfo);
		//注册成功封装要展示的用户信息
		ClaimsOfUserInfo claimsOfUserInfo = new ClaimsOfUserInfo(userInfo.getUserId(),
																 userInfo.getNickName(),
																 userInfo.getAvatar(),
																 userInfo.getSex(),
																 userInfo.getBirthday(),
																 userInfo.getPersonIntroduction(),
																 userInfo.getNoticeInfo());
		ClaimsOfUserInfo Md5Claims = claimsOfUserInfo.encryptOfMd5(claimsOfUserInfo);
		String jwt = JwtUtils.generateJwt(Md5Claims);
		//用户数据存入Redis
		redisDataMapper.setData("user:"+jwt, claimsOfUserInfo,60*60*3);

		return jwt;
	}

	/**ljz
	 * 获取用户数据
	 */
	public ClaimsOfUserInfo getByTokenOfUser(String key){

		ClaimsOfUserInfo claimsOfUserInfo = redisDataMapper.getByKey("user:"+key,ClaimsOfUserInfo.class);

		if(claimsOfUserInfo != null){
			return claimsOfUserInfo;
		}
		Claims claims =JwtUtils.parseJWT(key);
		String userId = (String) claims.get("userId");
		UserInfo userInfo = userInfoMappers.selectByUserId(userId);
		ClaimsOfUserInfo newClaimsOfUserInfo = new ClaimsOfUserInfo(userInfo.getUserId(),userInfo.getNickName(),userInfo.getAvatar(),userInfo.getSex(),userInfo.getBirthday(),userInfo.getPersonIntroduction(),userInfo.getNoticeInfo());

		redisDataMapper.setData("user:"+key,newClaimsOfUserInfo,60*60*3);
		return newClaimsOfUserInfo;
	}

	/**ljz
	 * 更新两个数据库中用户数据
	 */
	@Override
	public void updateByTokenOfUser(ClaimsOfUserInfo claimsOfUserInfo, String key) {
		//获取userID
		Claims claims =JwtUtils.parseJWT(key);
		String userId = (String) claims.get("userId");

		//数据库更新
		userInfoMappers.updateByUserId(new UserInfo(claimsOfUserInfo),userId);
		//redis库更新
		if(!redisDataMapper.updateByKey(key,claimsOfUserInfo)) {
			UserInfo userInfo = userInfoMappers.selectByUserId(userId);
			ClaimsOfUserInfo newClaimsOfUserInfo = new ClaimsOfUserInfo(userInfo.getUserId(), userInfo.getNickName(), userInfo.getAvatar(), userInfo.getSex(), userInfo.getBirthday(), userInfo.getPersonIntroduction(), userInfo.getNoticeInfo());
			redisDataMapper.setData("user:" + key, newClaimsOfUserInfo, 60 * 60 * 3);
		}
	}

	/**ljz
	 * 更新数据库中用户密码
	 */
	@Override
	public void updatePasswordByToken(String oldPassword, String newPassword, String key) throws BusinessException {
		//获取userID
		Claims claims =JwtUtils.parseJWT(key);
		String userId = (String) claims.get("userId");
		//检测新密码是否为空，为空，默认用户不更改密码
		if(!StringUtils.hasLength(newPassword)){
			return;
		}
		//原密码检验
		if(!StringUtils.hasLength(oldPassword)){
			throw new BusinessException(600,"请输入原密码");
		}
		if(!userInfoMappers.selectByUserId(userId).getPassword().equals(DigestUtils.md5Hex(oldPassword))){
			throw new BusinessException(600,"原密码错误");
		}
		//新密码格式检验
		Pattern patPassword = Pattern.compile(Constants.REGEX_PASSWORD);
		Matcher matPassword = patPassword.matcher(newPassword);
		if(!matPassword.matches()){
			throw new BusinessException(600,"新密码格式错误");
		}
		//更改密码
		UserInfo userInfo = new UserInfo();
		userInfo.setPassword(DigestUtils.md5Hex(newPassword));
		userInfoMappers.updateByUserId(userInfo,userId);
	}

}