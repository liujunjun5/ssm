package com.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunyuan
 * @date 2022/1/28 19:22
 */
@Controller
public class UserController {
    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "OK";
    }
//262665

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login.jsp";
    }
}
