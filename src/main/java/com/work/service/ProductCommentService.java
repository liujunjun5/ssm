package com.work.service;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.work.entity.po.ProductComment;
import com.work.entity.query.ProductCommentQuery;
import com.work.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description:评论Service
 * @date:2024-12-05
 * @author: liujun
 */
public interface ProductCommentService { 

	/**
	 * 根据条件查询列表
	 */
	List<ProductComment> findListByParam(ProductCommentQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ProductCommentQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ProductComment> findByPage(ProductCommentQuery query);

	/**
	 * 新增
	 */
	Integer add(ProductComment bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ProductComment> listBean);

	/**
	 * 批量新增或更新
	 */
	Integer addOrUpdateBatch(List<ProductComment> listBean);


	/**
	 * 根据CommentId查询
	 */
	ProductComment getByCommentId(Integer commentId);

	/**
	 * 根据CommentId更新
	 */
	Integer updateByCommentId(ProductComment bean, Integer commentId);

	/**
	 * 根据CommentId删除
	 */
	Integer deleteByCommentId(Integer commentId);

}