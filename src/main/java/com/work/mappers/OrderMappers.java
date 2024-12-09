package com.work.mappers;

import com.work.entity.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMappers<T> extends BaseMapper{
    Integer add(@Param("bean") T t);

    List<Order> findOrder(String payer);
}
