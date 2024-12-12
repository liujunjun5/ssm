package com.work.service;

import com.work.entity.po.Order;

import java.util.List;

public interface OrderService {
    Integer add(Order bean);

    List<Order> findOrder(String payer);

    boolean findRecord(String userId, String productId);
}
