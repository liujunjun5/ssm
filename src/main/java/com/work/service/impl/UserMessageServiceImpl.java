package com.work.service.impl;

import com.work.service.UserMessageService;
import com.work.entity.po.UserMessage;
import com.work.entity.query.SimplePage;
import com.work.entity.query.UserMessageQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.enums.PageSize;
import com.work.mappers.UserMessageMappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:用户消息表Service
 * @date:2024-12-08
 * @author: liujun
 */
@Service("UserMessageService")
public class UserMessageServiceImpl implements UserMessageService {

	@Resource
	private UserMessageMappers<UserMessage, UserMessageQuery> userMessageMappers;

	/**
	 * 根据条件查询列表
	 */
	public List<UserMessage> findListByParam(UserMessageQuery query) {
		return this.userMessageMappers.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(UserMessageQuery query) {
		return this.userMessageMappers.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<UserMessage> findByPage(UserMessageQuery query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize()==null?PageSize.SIZE15.getSize():query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserMessage> list = this.findListByParam(query);
		PaginationResultVO<UserMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(UserMessage bean) {
		return this.userMessageMappers.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<UserMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userMessageMappers.insertBatch(listBean);
	}

	/**
	 * 批量新增或更新
	 */
	public Integer addOrUpdateBatch(List<UserMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userMessageMappers.insertOrUpdateBatch(listBean);
	}


	/**
	 * 根据MessageId查询
	 */
	public UserMessage getByMessageId(Integer messageId) {
		return this.userMessageMappers.selectByMessageId(messageId);
	}

	/**
	 * 根据MessageId更新
	 */
	public Integer updateByMessageId(UserMessage bean, Integer messageId) {
		return this.userMessageMappers.updateByMessageId(bean, messageId);
	}

	/**
	 * 根据MessageId删除
	 */
	public Integer deleteByMessageId(Integer messageId) {
		return this.userMessageMappers.deleteByMessageId(messageId);
	}

	/**
	 * 查找最大messageId
	 */
	public Integer findMaxMessageId(){
		Integer MaxMessageId = this.userMessageMappers.findMaxMessageId();
		return MaxMessageId==null?0:MaxMessageId;
	};

}