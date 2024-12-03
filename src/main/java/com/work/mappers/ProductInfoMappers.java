package com.work.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:商品信息Mapper
 * @date:2024-12-03
 * @author: liujun
 */
public interface ProductInfoMappers<T, P> extends BaseMapper {

	/**
	 * 根据ProductId查询
	 */
	T selectByProductId(@Param("productId") String productId);

	/**
	 * 根据ProductId更新
	 */
	Integer updateByProductId(@Param("bean") T t, @Param("productId") String productId);

	/**
	 * 根据ProductId删除
	 */
	Integer deleteByProductId(@Param("productId") String productId);

}