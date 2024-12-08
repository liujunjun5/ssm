package com.work.service.impl;

import com.work.entity.po.RateInfo;
import com.work.mappers.RateInfoMappers;
import com.work.service.RateInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RateInfoService")
public class RateInfoServiceImpl implements RateInfoService {
    @Resource
    private RateInfoMappers<RateInfo> rateInfoMappers;
    public List<Integer> findRateByParam(String productId) {
        return this.rateInfoMappers.findRateByProductId(productId);
    }


    public List<RateInfo> findRateList(String userId) {
        return this.rateInfoMappers.findRateList(userId);
    }

    public void addRateByProductId(RateInfo bean) {
        this.rateInfoMappers.addRateByProductId(bean);
    }


    public void updateRateByProductId(String productId,String userId,Integer rate) {
        this.rateInfoMappers.updateRateByProductId(productId,userId,rate);
    }

    @Override
    public void deleteRate(String productId, String userId) {
        this.rateInfoMappers.deleteRate(productId,userId);
    }

    public Integer findRate(String productId,String userId){
        return this.rateInfoMappers.findRate(productId,userId);
    }
}
