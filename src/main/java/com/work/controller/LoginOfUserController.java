package com.work.controller;

import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.vo.ResponseVO;
import com.work.service.CheckCodeService;
import com.work.service.UserInfoService;
import com.work.utils.ParamCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//ljz
@Slf4j
@RestController
@RequestMapping("/account")
public class LoginOfUserController extends ABaseController{
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CheckCodeService checkCodeService;

//    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/checkCode")
    public ResponseVO checkCode(){

        Map<String, String> theCheckCode =  checkCodeService.setCheckCode();

        return getSuccessResponseVO(theCheckCode);

    }

    @PostMapping("/login")
    public ResponseVO login(HttpServletResponse response,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String checkCodeKey,
                            @RequestParam String checkCode,
                            @RequestParam(defaultValue = "0") Integer autoLogin){
        //参数非空检验
        if(ParamCheckUtils.checkLoginParam(email,password,checkCode) != null){
            return getServerErrorResponseVO(ParamCheckUtils.checkLoginParam(email,password,checkCode));
        }

        //验证码校验
        if(!checkCodeService.checkCode(checkCodeKey,checkCode)){
            return getServerErrorResponseVO("验证码错误");
        }

        //查询用户是否存在
        String jwt = userInfoService.findOneByParam(email,DigestUtils.md5Hex(password));
        if(jwt == null){
            return getServerErrorResponseVO("账户不存在");
        }

        //下发Jwt令牌
        saveJwtCookie(response,jwt,autoLogin);

        return getSuccessResponseVO("登录成功");

    }

    @PostMapping("/register")
    public ResponseVO register(HttpServletResponse response,
                               @RequestParam(required = false) String nickName,
                               @RequestParam String email,
                               @RequestParam String registerPassword,
                               @RequestParam String checkCodeKey,
                               @RequestParam String checkCode,
                               @RequestParam(defaultValue = "0") Integer autoLogin){
        //参数非空检验
        if(ParamCheckUtils.checkLoginParam(email,registerPassword,checkCode) != null){
            return getServerErrorResponseVO(ParamCheckUtils.checkLoginParam(email,registerPassword,checkCode));
        }
        //验证码校验
        if(!checkCodeService.checkCode(checkCodeKey,checkCode)){
            return getServerErrorResponseVO("验证码错误");
        }

        //注册
        String jwt = userInfoService.register(nickName,email,registerPassword);
        if(jwt == null){
            return getServerErrorResponseVO("账户已存在");
        }

        //下发Jwt令牌
        saveJwtCookie(response,jwt,autoLogin);

        return getSuccessResponseVO("注册成功");

    }

    @GetMapping("/logout")
    public ResponseVO logout(HttpServletResponse response,
                             HttpServletRequest request){
        cleanJwtCookie(response,request);
        return getSuccessResponseVO("登出成功");
    }


    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return getServerErrorResponseVO("用户登录状态异常");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.TOKEN_KEY)) {

                ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenFromRedis(cookie.getValue());
                return getSuccessResponseVO(claimsOfUserInfo);
            }
        }
        return getServerErrorResponseVO("用户登录状态异常");

    }


    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(ClaimsOfUserInfo claimsOfUserInfo, HttpServletRequest request){
        //参数全空检验
        if(ParamCheckUtils.areAllPropertiesEmpty(claimsOfUserInfo) != null){
            return getServerErrorResponseVO(ParamCheckUtils.areAllPropertiesEmpty(claimsOfUserInfo));
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return getServerErrorResponseVO("用户登录状态异常");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.TOKEN_KEY)) {

                userInfoService.updateByTokenOfUser(claimsOfUserInfo,cookie.getValue());
                return getSuccessResponseVO("更新成功");
            }
        }
        return getServerErrorResponseVO("用户登录状态异常");

    }

    @GetMapping("/test")
    public ResponseVO test(User user){
        log.info("451165651");

        return getServerErrorResponseVO(user);

    }

//    public ResponseVO updateUserInfo(@RequestParam(required = false) String nickName,
//                                     @RequestParam(required = false) String avatar,
//                                     @RequestParam(required = false) Integer sex,
//                                     @RequestParam(required = false) String birthday,
//                                     @RequestParam(required = false) String personIntroduction,
//                                     @RequestParam(required = false) String noticeInfo){
//        //参数全空检验
//        if(ParamCheckUtils.checkUpdateParam(nickName,avatar,sex,birthday,personIntroduction,noticeInfo) != null){
//            return getServerErrorResponseVO(ParamCheckUtils.checkUpdateParam(nickName,avatar,sex,birthday,personIntroduction,noticeInfo));
//        }
//
//        userInfoService.updateByUserIdOfUser(nickName,avatar,sex,birthday,personIntroduction,noticeInfo);
//
//    }



//    @GetMapping("/t1")
//    public void test(){
//        User user = new User("李四",19);
//
//        // 插入一条string类型数据
//        String json = JSON.toJSONString(user);
//        stringRedisTemplate.opsForValue().set("user:100", json);
//        //读取一条string类型数据
//        String val = stringRedisTemplate.opsForValue().get("user:100");
//        User user1 = JSON.parseObject(val,User.class);
//        System.out.println("user100 = "+user);
//    }
}
