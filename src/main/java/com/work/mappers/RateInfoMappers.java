package com.work.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RateInfoMappers <T, P> extends BaseMapper{
    List<Integer> findRateByProductId(@Param("bean") T t, @Param("productId") String productId);

    Integer addRateByProductId(@Param("bean") T t, @Param("productId") String productId);
}
