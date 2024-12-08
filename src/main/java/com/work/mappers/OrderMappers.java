package com.work.mappers;

import org.apache.ibatis.annotations.Param;

public interface OrderMappers<T> extends BaseMapper{
    Integer add(@Param("bean") T t);
}
