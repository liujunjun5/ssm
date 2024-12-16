package com.work.interceptor;

import com.work.entity.constants.Constants;
import com.work.exception.BusinessException;
import com.work.utils.CookieUtils;
import com.work.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ManagementInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtInToken = CookieUtils.getCookie(request, Constants.ADMIN_KEY);

        if(!StringUtils.hasLength(jwtInToken)){
            throw new BusinessException(902,"您无权访问此页面");
        }

        return true;
    }
}
