package com.work.service.impl;

import com.alibaba.fastjson.JSON;
import com.work.entity.constants.Constants;
import com.work.entity.po.Administrator;
import com.work.entity.po.UserInfo;
import com.work.entity.query.UserInfoQuery;
import com.work.exception.BusinessException;
import com.work.mappers.UserInfoMappers;
import com.work.service.AdminService;
import com.work.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserInfoMappers<UserInfo, UserInfoQuery> userInfoMappers;

    @Override
    public String checkAccount(Administrator administrator) throws BusinessException {

        if(administrator.getAccount().equals(Constants.ADMIN_ACCOUNT)&&administrator.getPassword().equals(Constants.ADMIN_PASSWORD)) {

            //对管理员信息做加密操作，生成Jwt令牌
            administrator.setAccount(DigestUtils.md5Hex(administrator.getAccount()));
            administrator.setPassword(DigestUtils.md5Hex(administrator.getPassword()));
            String jwt = JwtUtils.generateJwt(administrator);
            return jwt;
        }else {
            throw new BusinessException(600,"管理员验证失败");
        }
    }

    @Override
    public void setUserStatus(String userId, Integer status) throws BusinessException {

        //创建更新条件对象
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        //更新
        if(userInfoMappers.updateByUserId(userInfo,userId) == 0){
            //未找到用户报错
            throw new BusinessException(602,"用户不存在");
        }
    }
}
