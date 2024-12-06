package com.work.service;

import java.util.Map;

public interface CheckCodeService {


    boolean checkCode(String checkCodeKey,String checkCode);


    Map<String, String> setCheckCode();
}
