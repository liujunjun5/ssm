package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.Administrator;
import com.work.entity.query.UserInfoQuery;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.AdminService;
import com.work.service.CheckCodeService;
import com.work.service.UserInfoService;
import com.work.utils.CookieUtils;
import com.work.utils.ParamCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/account")
public class AdminAccountController extends ABaseController{


    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/checkCode")
    public ResponseVO checkCode() throws BusinessException {

        Map<String, String> theCheckCode = checkCodeService.setCheckCode();
        return getSuccessResponseVO(theCheckCode);


    }

    @PostMapping("/login")
    public ResponseVO login(HttpServletResponse response,
                            Administrator administrator,
                            @RequestParam String checkCodeKey,
                            @RequestParam String checkCode) throws BusinessException {
        //参数检验
        if(ParamCheckUtils.checkLoginParam(administrator.getAccount(),administrator.getPassword(),checkCode) != null){
            throw new BusinessException(600,ParamCheckUtils.checkLoginParam(administrator.getAccount(),administrator.getPassword(),checkCode));
        }

        //验证码校验
        if(!checkCodeService.checkCode(checkCodeKey,checkCode)){
            throw new BusinessException(600,"验证码错误");
        }

        //验证管理员身份
        String jwt = adminService.checkAccount(administrator);

        //下发Jwt令牌
        CookieUtils.saveCookie(response, Constants.ADMIN_KEY,jwt,Constants.TIME_SECONDS_DAY,1);

        return getSuccessResponseVO("管理员验证成功");

    }

    @GetMapping("/logout")
    public ResponseVO logout(HttpServletResponse response,
                             HttpServletRequest request) throws BusinessException {
        //清除cookie中的登录令牌
        CookieUtils.cleanCookie(response,request,Constants.ADMIN_KEY);

        return getSuccessResponseVO("登出成功");
    }

    @GetMapping("/userStatus")
    public ResponseVO setUserStatus(String userId,Integer status) throws BusinessException {
        //对用户状态做管理操作
        adminService.setUserStatus(userId,status);

        return getSuccessResponseVO("操作成功");
    }

    @GetMapping("/loadUserInfo")
    public ResponseVO getAllUserInfo(Integer pageSize, Integer pageNo, String orderBy, String direction){

        UserInfoQuery userInfoQuery = new UserInfoQuery();
        userInfoQuery.setPageSize(pageSize);
        userInfoQuery.setPageNo(pageNo);
        userInfoQuery.setOrderBy(orderBy);
        userInfoQuery.setDirection(direction);

        return getSuccessResponseVO(userInfoService.findByPage(userInfoQuery));
    }
}
