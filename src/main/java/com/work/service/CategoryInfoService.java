package com.work.service;

import com.work.entity.po.CategoryInfo;
import com.work.entity.query.CategoryInfoQuery;
import com.work.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description:分类信息Service
 * @date:2024-12-03
 * @author: liujun
 */
public interface CategoryInfoService { 

	/**
	 * 根据条件查询列表
	 */
	List<CategoryInfo> findListByParam(CategoryInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(CategoryInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<CategoryInfo> findByPage(CategoryInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(CategoryInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<CategoryInfo> listBean);

	/**
	 * 批量新增或更新
	 */
	Integer addOrUpdateBatch(List<CategoryInfo> listBean);


	/**
	 * 根据CategoryId查询
	 */
	CategoryInfo getByCategoryId(Integer categoryId);

	/**
	 * 根据CategoryId更新
	 */
	Integer updateByCategoryId(CategoryInfo bean, Integer categoryId);

	/**
	 * 根据CategoryId删除
	 */
	Integer deleteByCategoryId(Integer categoryId);


	/**
	 * 根据CategoryCode查询
	 */
	CategoryInfo getByCategoryCode(String categoryCode);

	/**
	 * 根据CategoryCode更新
	 */
	Integer updateByCategoryCode(CategoryInfo bean, String categoryCode);

	/**
	 * 根据CategoryCode删除
	 */
	Integer deleteByCategoryCode(String categoryCode);

	/**
	 * 寻找最大sort字段
	 */
	Integer findMaxSort();

}