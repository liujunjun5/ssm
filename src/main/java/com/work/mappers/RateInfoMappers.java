package com.work.mappers;

import com.work.entity.po.RateInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RateInfoMappers <T> extends BaseMapper{
    List<Integer> findRateByProductId(@Param("productId") String productId);

    List<RateInfo> findRateList(@Param("userId") String userId);

    Integer addRateByProductId(@Param("bean") T t);

    void updateRateByProductId(@Param("productId")String productId,@Param("userId")String userId,@Param("rate")Integer rate);

    Integer findRate(@Param("productId")String productId,@Param("userId")String userId);

    void deleteRate(@Param("productId")String productId, @Param("userId")String userId);
}
