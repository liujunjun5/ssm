package com.work.service;

import com.work.entity.po.RateInfo;

import java.util.List;

public interface RateInfoService {
    List<Integer> findRateByParam(RateInfo bean, String productId);

    Integer addRateByProductId(RateInfo bean, String productId);

    void updateRateByProductId(String productId,String userId,Integer rate);
}
