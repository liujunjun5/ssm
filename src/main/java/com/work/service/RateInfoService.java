package com.work.service;

import com.work.entity.po.RateInfo;

import java.util.List;

public interface RateInfoService {
    List<Integer> findRateByParam(String productId);

    Integer addRateByProductId(RateInfo bean);

    void updateRateByProductId(String productId,String userId,Integer rate);

    Integer findRate(String productId,String userId);
}
