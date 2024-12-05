package com.work.service;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.work.entity.po.BrandInfo;
import com.work.entity.query.BrandInfoQuery;
import com.work.entity.vo.PaginationResultVO;

import java.util.List;
/**
 * @Description:品牌信息Service
 * @date:2024-12-05
 * @author: liujun
 */
public interface BrandInfoService { 

	/**
	 * 根据条件查询列表
	 */
	List<BrandInfo> findListByParam(BrandInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(BrandInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<BrandInfo> findByPage(BrandInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(BrandInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<BrandInfo> listBean);

	/**
	 * 批量新增或更新
	 */
	Integer addOrUpdateBatch(List<BrandInfo> listBean);


	/**
	 * 根据BrandId查询
	 */
	BrandInfo getByBrandId(Integer brandId);

	/**
	 * 根据BrandId更新
	 */
	Integer updateByBrandId(BrandInfo bean, Integer brandId);

	/**
	 * 根据BrandId删除
	 */
	Integer deleteByBrandId(Integer brandId);

}