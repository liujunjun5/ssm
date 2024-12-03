package com.work.service.impl;


import com.work.entity.po.UserInfo;
import com.work.entity.query.SimplePage;
import com.work.entity.query.UserInfoQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.mappers.UserInfoMappers;
import com.work.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description:用户信息Service
 * @date:2024-12-02
 * @author: liujun
 */
@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMappers<UserInfo, UserInfoQuery> userInfoMappers;

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
	public Integer updateByUserId(UserInfo bean, String userId) {
		return this.userInfoMappers.updateByUserId(bean, userId);
	}

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

}