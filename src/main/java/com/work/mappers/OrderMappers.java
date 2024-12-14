package com.work.mappers;

import com.work.entity.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMappers<T> extends BaseMapper{
    Integer add(@Param("bean") T t);

    List<Order> findOrder(@Param("payer")String payer);

    List<Order> findRecord(@Param("userId")String userId, @Param("productId")String productId);

    List<Order> bossFindOrder(@Param("payee")String payee);
}
