package com.work.interceptor;

import com.work.entity.constants.Constants;
import com.work.exception.BusinessException;
import com.work.utils.CookieUtils;
import com.work.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("这是我配置的拦截器...............................................................................................");
        String jwtInToken = CookieUtils.getCookie(request, Constants.TOKEN_KEY) == null?CookieUtils.getCookie(request, Constants.ADMIN_KEY):CookieUtils.getCookie(request, Constants.TOKEN_KEY);

        try {
            if(!StringUtils.hasLength(jwtInToken)){
                throw new Exception();
            }
            JwtUtils.parseJWT(jwtInToken);
        } catch (Exception e) {

            if(request.getRequestURI().toString().contains("admin")){
                throw new BusinessException(901,"管理员登录状态异常，请重新登录");
            }else {
                throw new BusinessException(901,"用户登录状态异常，请重新登录");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("登录拦截器2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("登录拦截器3");
    }
}
