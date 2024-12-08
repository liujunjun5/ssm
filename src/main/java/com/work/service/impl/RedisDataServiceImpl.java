package com.work.service.impl;

import com.work.exception.BusinessException;
import com.work.mappers.RedisDataMapper;
import com.work.service.RedisDataService;
import com.work.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("RedisDataService")
public class RedisDataServiceImpl implements RedisDataService {

    @Autowired
    private RedisDataMapper redisDataMapper;

    @Override
    public void deleteByToken(HttpServletRequest request, String cookieKey,String Object) throws BusinessException {

        String redisUserInfoKey = Object+CookieUtils.getCookie(request, cookieKey);

        if (CookieUtils.getCookie(request, cookieKey) == null){
            throw new BusinessException(901,"用户未登录，请求不合法");
        }

        redisDataMapper.deleteByKey(redisUserInfoKey);

    }

    @Override
    public <T> T getByToken(HttpServletRequest request, String cookieKey, String Object, Class<T> clazz) throws BusinessException {

        String redisUserInfoKey = Object+CookieUtils.getCookie(request, cookieKey);

        if (CookieUtils.getCookie(request, cookieKey) == null){
            throw new BusinessException(901,"用户未登录，请求不合法");
        }

        return redisDataMapper.getByKey(redisUserInfoKey,clazz);
    }

    @Override
    public <T> void updateByToken(HttpServletRequest request, String cookieKey, String Object,T newData) throws BusinessException {

        String redisUserInfoKey = Object+CookieUtils.getCookie(request, cookieKey);

        if (CookieUtils.getCookie(request, cookieKey) == null){
            throw new BusinessException(901,"用户未登录，请求不合法");
        }

        redisDataMapper.updateByKey(redisUserInfoKey,newData);
    }


//    @Override
//    public void deleteByKey(String key) {
//        stringRedisTemplate.delete(key);
//    }
//
//    @Override
//    public <T> T getByKey(String key, Class<T> clazz) {
//
//        String json = stringRedisTemplate.opsForValue().get(key);
//
//        return JSON.parseObject(json,clazz);
//    }

//    @Override
//    public Object getByKey(String key) {
//
//        String json = stringRedisTemplate.opsForValue().get(key);
//        ClaimsOfUserInfo claimsOfRedis = JSON.parseObject(json,ClaimsOfUserInfo.class);
//
//        return claimsOfRedis;
//    }

}
