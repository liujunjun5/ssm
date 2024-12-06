package com.work.service.impl;

import com.alibaba.fastjson.JSON;
import com.wf.captcha.ArithmeticCaptcha;
import com.work.service.CheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**ljz
     * 校验验证码
     */
    @Override
    public boolean checkCode(String checkCodeKey, String checkCode) {
        //从Redis中读取验证码正确答案
        String finalCheckCodeKey = "checkCodeKey:"+checkCodeKey;
        String rightCheckCode = stringRedisTemplate.opsForValue().get(finalCheckCodeKey);
        //校验
        if (checkCode.equals(rightCheckCode)){
            return true;
        }
        return false;
    }

    /**ljz
     * 生成验证码
     */
    @Override
    public Map<String, String> setCheckCode() {
        //生成验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
        String code = captcha.text();
        //存入Redis
        String checkCodeKey = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set("checkCodeKey:"+checkCodeKey, code);
        //返回验证码
        String checkCodeBase64 = captcha.toBase64();
        Map<String, String> theCheckCode = new HashMap<>();
        theCheckCode.put("checkCodeKey",checkCodeKey);
        theCheckCode.put("checkCode",checkCodeBase64);

        return theCheckCode;
    }
}
