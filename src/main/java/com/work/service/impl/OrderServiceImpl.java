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


    public Boolean add(List<Order> orders) {
        for (Order order : orders) {
            // 在这里可以进行一些验证，比如订单是否为null等
            if (order != null) {
                this.orderMappers.add(order);
            }
        }
        return true;
    }

    public List<Order> findOrder(String payer) {
        return this.orderMappers.findOrder(payer);
    }


    public boolean findRecord(String userId, String productId) {
        return !this.orderMappers.findRecord(userId, productId).isEmpty();
    }

    @Override
    public List<Order> bossFindOrder(String payee) {
        return this.orderMappers.bossFindOrder(payee);
    }
}
