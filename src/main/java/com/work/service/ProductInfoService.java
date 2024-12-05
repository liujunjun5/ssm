package com.work.service;

import com.work.entity.po.ProductInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description:商品信息Service
 * @date:2024-12-03
 * @author: liujun
 */
public interface ProductInfoService { 

	/**
	 * 根据条件查询列表
	 */
	List<ProductInfo> findListByParam(ProductInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ProductInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ProductInfo> findByPage(ProductInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(ProductInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ProductInfo> listBean);

	/**
	 * 批量新增或更新
	 */
	Integer addOrUpdateBatch(List<ProductInfo> listBean);


	/**
	 * 根据ProductId查询
	 */
	ProductInfo getByProductId(String productId);

	/**
	 * 根据ProductId更新
	 */
	Integer updateByProductId(ProductInfo bean, String productId);

	/**
	 * 根据ProductId删除
	 */
	Integer deleteByProductId(String productId);

}