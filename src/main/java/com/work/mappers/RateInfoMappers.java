package com.work.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RateInfoMappers <T, P> extends BaseMapper{
    List<Integer> findRateByProductId(@Param("productId") String productId);

    Integer addRateByProductId(@Param("bean") T t);

    void updateRateByProductId(@Param("productId")String productId,@Param("userId")String userId,@Param("rate")Integer rate);

    Integer findRate(@Param("productId")String productId,@Param("userId")String userId);
}
