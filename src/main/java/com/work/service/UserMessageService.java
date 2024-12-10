package com.work.service;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.work.entity.po.UserMessage;
import com.work.entity.query.UserMessageQuery;
import com.work.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description:用户消息表Service
 * @date:2024-12-08
 * @author: liujun
 */
public interface UserMessageService { 

	/**
	 * 根据条件查询列表
	 */
	List<UserMessage> findListByParam(UserMessageQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(UserMessageQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserMessage> findByPage(UserMessageQuery query);

	/**
	 * 新增
	 */
	Integer add(UserMessage bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserMessage> listBean);

	/**
	 * 批量新增或更新
	 */
	Integer addOrUpdateBatch(List<UserMessage> listBean);


	/**
	 * 根据MessageId查询
	 */
	UserMessage getByMessageId(Integer messageId);

	/**
	 * 根据MessageId更新
	 */
	Integer updateByMessageId(UserMessage bean, Integer messageId);

	/**
	 * 根据MessageId删除
	 */
	Integer deleteByMessageId(Integer messageId);

	/**
	 * 查找最大messageId
	 */
	Integer findMaxMessageId();

}