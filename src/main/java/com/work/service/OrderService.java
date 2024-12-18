package com.work.service;

import com.work.entity.po.Order;

import java.util.List;

public interface OrderService {
    Boolean add(List<Order> orders);

    List<Order> findOrder(String payer);

    boolean findRecord(String userId, String productId);

    List<Order> bossFindOrder(String payee);
}
