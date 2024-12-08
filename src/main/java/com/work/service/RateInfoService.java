package com.work.service;

import com.work.entity.po.RateInfo;

import java.util.List;

public interface RateInfoService {
    List<Integer> findRateByParam(String productId);

    List<RateInfo> findRateList(String userId);

    void addRateByProductId(RateInfo bean);

    void updateRateByProductId(String productId,String userId,Integer rate);

    void deleteRate(String productId,String userId);

    Integer findRate(String productId,String userId);
}
