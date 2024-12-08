package com.work.service;

import com.work.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;

public interface RedisDataService {

    void deleteByToken(HttpServletRequest request,String cookieKey,String Object) throws BusinessException;

    <T> T getByToken(HttpServletRequest request,String cookieKey,String Object,Class<T> clazz) throws BusinessException;

    <T> void updateByToken(HttpServletRequest request,String cookieKey,String Object,T newData) throws BusinessException;
}
