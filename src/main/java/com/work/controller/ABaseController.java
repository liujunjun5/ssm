package com.work.controller;


import com.work.entity.vo.ResponseVO;
import com.work.enums.ResponseCodeEnum;
import com.work.entity.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ABaseController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    protected static final String STATUC_SUCCESS = "success";

    protected static final String STATUC_ERROR = "error";

    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUC_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }


    protected <T> ResponseVO getServerErrorResponseVO(T t) {
        ResponseVO vo = new ResponseVO();
        vo.setStatus(STATUC_ERROR);
        vo.setCode(ResponseCodeEnum.CODE_500.getCode());
        vo.setInfo(ResponseCodeEnum.CODE_500.getMsg());
        vo.setData(t);
        return vo;
    }
    protected <T> ResponseVO getServerErrorIdResponseVO(T t) {
        ResponseVO vo = new ResponseVO();
        vo.setStatus(STATUC_ERROR);
        vo.setCode(ResponseCodeEnum.CODE_601.getCode());
        vo.setInfo(ResponseCodeEnum.CODE_601.getMsg());
        vo.setData(t);
        return vo;
    }

    protected <T> ResponseVO getServerErrorProductResponseVO(T t) {
        ResponseVO vo = new ResponseVO();
        vo.setStatus(STATUC_ERROR);
        vo.setCode(ResponseCodeEnum.CODE_602.getCode());
        vo.setInfo(ResponseCodeEnum.CODE_602.getMsg());
        vo.setData(t);
        return vo;
    }


    /**ljz
     * token清除
     **/
    public void cleanJwtCookie(HttpServletResponse response,
                               HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.TOKEN_KEY)) {

                stringRedisTemplate.delete("user:"+cookie.getValue());
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
    }
    
}
