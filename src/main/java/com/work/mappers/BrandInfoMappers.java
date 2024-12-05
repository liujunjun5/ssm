package com.work.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:品牌信息Mapper
 * @date:2024-12-05
 * @author: liujun
 */
public interface BrandInfoMappers<T, P> extends BaseMapper {

	/**
	 * 根据BrandId查询
	 */
	T selectByBrandId(@Param("brandId") Integer brandId);

	/**
	 * 根据BrandId更新
	 */
	Integer updateByBrandId(@Param("bean") T t, @Param("brandId") Integer brandId);

	/**
	 * 根据BrandId删除
	 */
	Integer deleteByBrandId(@Param("brandId") Integer brandId);

}