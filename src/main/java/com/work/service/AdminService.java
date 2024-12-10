package com.work.service;

import com.work.entity.po.Administrator;
import com.work.exception.BusinessException;

public interface AdminService {

    String checkAccount(Administrator administrator) throws BusinessException;

    void setUserStatus(String userId, Integer status) throws BusinessException;
}
