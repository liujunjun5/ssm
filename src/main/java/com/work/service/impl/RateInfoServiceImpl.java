package com.work.service.impl;

import com.work.entity.po.RateInfo;
import com.work.entity.query.ProductInfoQuery;
import com.work.mappers.RateInfoMappers;
import com.work.service.RateInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RateInfoService")
public class RateInfoServiceImpl implements RateInfoService {
    @Resource
    private RateInfoMappers<RateInfo, ProductInfoQuery> rateInfoMappers;
    public List<Integer> findRateByParam(String productId) {
        return this.rateInfoMappers.findRateByProductId(productId);
    }

    public Integer addRateByProductId(RateInfo bean) {
        return this.rateInfoMappers.addRateByProductId(bean);
    }


    public void updateRateByProductId(String productId,String userId,Integer rate) {
        this.rateInfoMappers.updateRateByProductId(productId,userId,rate);
    }

    public Integer findRate(String productId,String userId){
        return this.rateInfoMappers.findRate(productId,userId);
    }
}
