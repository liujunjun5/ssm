package com.work.service.impl;


import com.wf.captcha.ArithmeticCaptcha;

import com.work.exception.BusinessException;
import com.work.mappers.RedisDataMapper;
import com.work.service.CheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @Autowired
//    @Qualifier("RedisDataMapper")
    private RedisDataMapper redisDataMapper;

    /**ljz
     * 校验验证码
     */
    @Override
    public boolean checkCode(String checkCodeKey, String checkCode) {
        //从Redis中读取验证码正确答案
        String finalCheckCodeKey = "checkCodeKey:"+checkCodeKey;
//        String rightCheckCode = stringRedisTemplate.opsForValue().get(finalCheckCodeKey);
        String rightCheckCode = redisDataMapper.getValueByKey(finalCheckCodeKey);
        //校验
        return checkCode.equals(rightCheckCode);
    }

    /**ljz
     * 生成验证码
     */
    @Override
    public Map<String, String> setCheckCode() throws BusinessException {

        try {
            //生成验证码
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
            String code = captcha.text();
            //存入Redis,验证码有效时间为20秒
            String checkCodeKey = UUID.randomUUID().toString();
            redisDataMapper.setData("checkCodeKey:"+checkCodeKey,code,20);
            //返回验证码
            String checkCodeBase64 = captcha.toBase64();
            Map<String, String> theCheckCode = new HashMap<>();
            theCheckCode.put("checkCodeKey",checkCodeKey);
            theCheckCode.put("checkCode",checkCodeBase64);

            return theCheckCode;
        } catch (Exception e) {
            throw new BusinessException(500,"验证码生成失败");
        }
    }

}
