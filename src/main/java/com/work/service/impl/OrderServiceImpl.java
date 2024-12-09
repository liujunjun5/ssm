package com.work.service.impl;

import com.work.entity.po.Order;
import com.work.mappers.OrderMappers;
import com.work.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMappers<Order> orderMappers;


    public Integer add(Order bean) {
        return this.orderMappers.add(bean);
    }

    public List<Order> findOrder(String payer) {
        return this.orderMappers.findOrder(payer);
    }
}
