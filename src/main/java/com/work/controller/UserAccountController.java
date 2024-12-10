package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.UserInfo;
import com.work.entity.query.UserInfoQuery;
import com.work.entity.vo.ResponseVO;
import com.work.exception.BusinessException;
import com.work.service.CheckCodeService;
import com.work.service.RedisDataService;
import com.work.service.UserInfoService;
import com.work.utils.CookieUtils;
import com.work.utils.ParamCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//ljz
@Slf4j
@RestController
@RequestMapping("/account")
public class UserAccountController extends ABaseController{
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private RedisDataService redisDataService;

    @GetMapping("/checkCode")
    public ResponseVO checkCode() throws BusinessException {

        Map<String, String> theCheckCode = checkCodeService.setCheckCode();
        return getSuccessResponseVO(theCheckCode);

    }

    @PostMapping("/login")
    public ResponseVO login(HttpServletResponse response,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String checkCodeKey,
                            @RequestParam String checkCode,
                            @RequestParam(defaultValue = "0") Integer autoLogin) throws BusinessException {
        //参数检验
        if(ParamCheckUtils.checkLoginParam(email,password,checkCode) != null){
            throw new BusinessException(600,ParamCheckUtils.checkLoginParam(email,password,checkCode));
        }

        //验证码校验
        if(!checkCodeService.checkCode(checkCodeKey,checkCode)){
            throw new BusinessException(600,"验证码错误");
        }

        //查询用户是否存在
        String jwt = userInfoService.findOneByParam(new UserInfoQuery(email, DigestUtils.md5Hex(password)));

        //下发Jwt令牌
        if(autoLogin == 1){
            CookieUtils.saveCookie(response,Constants.TOKEN_KEY,jwt,Constants.TIME_SECONDS_DAY,15);
        }else {
            CookieUtils.saveCookie(response,Constants.TOKEN_KEY,jwt);
        }

        return getSuccessResponseVO("登录成功");

    }

    @PostMapping("/register")
    public ResponseVO register(HttpServletResponse response,
                               @RequestParam(required = false) String nickName,
                               @RequestParam String email,
                               @RequestParam String registerPassword,
                               @RequestParam String checkCodeKey,
                               @RequestParam String checkCode,
                               @RequestParam(defaultValue = "0") Integer autoLogin) throws BusinessException {
        //参数检验
        if(ParamCheckUtils.checkRegisterParam(email,registerPassword,checkCode) != null){
            throw new BusinessException(600,ParamCheckUtils.checkRegisterParam(email,registerPassword,checkCode));
        }

        //验证码校验
        if(!checkCodeService.checkCode(checkCodeKey,checkCode)){
            throw new BusinessException(600,"验证码错误");
        }

        //注册
        String jwt = userInfoService.register(new UserInfo(nickName,email,DigestUtils.md5Hex(registerPassword)));

        //下发Jwt令牌
        if(autoLogin == 1){
            CookieUtils.saveCookie(response,Constants.TOKEN_KEY,jwt,Constants.TIME_SECONDS_DAY,15);
        }else {
            CookieUtils.saveCookie(response,Constants.TOKEN_KEY,jwt);
        }

        return getSuccessResponseVO("注册成功");

    }

    @GetMapping("/logout")
    public ResponseVO logout(HttpServletResponse response,
                             HttpServletRequest request) throws BusinessException {
        //清除cookie中的登录令牌
        CookieUtils.cleanCookie(response,request,Constants.TOKEN_KEY);
        //清除redis里的用户数据
        redisDataService.deleteByToken(request,Constants.TOKEN_KEY,"user:");
        return getSuccessResponseVO("登出成功");
    }


    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo(HttpServletRequest request) throws BusinessException {

        //获取rediskey
        String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
        //获取redis里的用户信息并封装成用户信息对象
        ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);

        return getSuccessResponseVO(claimsOfUserInfo);

    }


    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(ClaimsOfUserInfo claimsOfUserInfo, HttpServletRequest request) throws BusinessException {

        //参数空串空值检验
        if(ParamCheckUtils.areAllPropertiesEmpty(claimsOfUserInfo) != null){
            throw new BusinessException(600,ParamCheckUtils.areAllPropertiesEmpty(claimsOfUserInfo));
        }
        //获取用户信息在redis中的key
        String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);

        if (redisUserInfoKey == null){
            throw new BusinessException(901,"用户未登录，请求不合法");
        }
        //更新数据库和redis里的用户信息
        userInfoService.updateByTokenOfUser(claimsOfUserInfo,redisUserInfoKey);

        return getSuccessResponseVO("更新成功");

    }

}
