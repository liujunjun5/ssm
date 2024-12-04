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
    public List<Integer> findRateByParam(RateInfo bean, String productId) {
        return this.rateInfoMappers.findRateByProductId(bean, productId);
    }

    public Integer addRateByProductId(RateInfo bean, String productId) {
        return this.rateInfoMappers.addRateByProductId(bean,productId);
    }
}
