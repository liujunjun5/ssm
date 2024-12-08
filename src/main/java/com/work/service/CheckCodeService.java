package com.work.service;

import com.work.exception.BusinessException;

import java.util.Map;

public interface CheckCodeService {


    boolean checkCode(String checkCodeKey,String checkCode);


    Map<String, String> setCheckCode() throws BusinessException;
}
