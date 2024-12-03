package com.work.controller;

import com.work.entity.vo.ResponseVO;
import com.work.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sunyuan
 * @date 2022/1/28 19:22
 */
@RestController
@RequestMapping("/test")
public class UserController extends ABaseController{

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/hello")
    public ResponseVO test() {
        return getSuccessResponseVO(userInfoService.getByUserId("1"));
    }
//不是牛俊
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login.jsp";
    }
}
