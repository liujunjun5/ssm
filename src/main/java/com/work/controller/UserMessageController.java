package com.work.controller;


import com.work.entity.constants.Constants;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.UserMessage;
import com.work.entity.query.UserMessageQuery;
import com.work.entity.vo.PaginationResultVO;
import com.work.entity.vo.ResponseVO;
import com.work.enums.MessageReadTypeEnum;

import com.work.exception.BusinessException;
import com.work.mappers.UserMessageMappers;
import com.work.service.UserInfoService;
import com.work.service.UserMessageService;
import com.work.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
public class UserMessageController extends ABaseController{
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserMessageMappers userMessageMappers;
    @Autowired
    private UserInfoService userInfoService;

    //加载单一类型消息
    @RequestMapping("/loadMessage")
    public ResponseVO loadMessage(Integer messageType, Integer pageNo ,HttpServletRequest request) throws BusinessException{
        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setMessageType(messageType);
        userMessageQuery.setPageNo(pageNo);
        userMessageQuery.setPageSize(Constants.LENGTH_15);

        try {
            //从请求中获取token字段，得到redis中的key
            //通过key得到对应对象json格式的字符串
            //反解json格式获得用户对象
            String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);
            //设置用户ID
            userMessageQuery.setUserId(claimsOfUserInfo.getUserId());
        } catch (Exception e) {
            throw new BusinessException("处理解析错误",e);
            // 处理解析错误
        }
        PaginationResultVO<UserMessage> resultVO = userMessageService.findByPage(userMessageQuery);
        //遍历结果集，将消息标记为已读
        if (resultVO != null && !resultVO.getList().isEmpty()) {
            for (UserMessage message : resultVO.getList()) {
                // 将消息标记为已读
                message.setReadType(1);
                // 保存更新
                userMessageService.updateByMessageId(message, message.getMessageId());
            }
        }
        return getSuccessResponseVO(resultVO);
    }


    //删除消息
    @RequestMapping("/delMessage")
    public ResponseVO delMessage(Integer messageId) {
        userMessageMappers.deleteByMessageId(messageId);
        return getSuccessResponseVO(messageId);
    }


    //获取未读消息数量
    @RequestMapping("/getNoReadCount")
    public ResponseVO getNoReadCount(HttpServletRequest request) throws BusinessException{
        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setReadType(MessageReadTypeEnum.NO_READ.getType());

        try {
            //获取用户对象
            String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);
            //设置用户ID
            userMessageQuery.setUserId(claimsOfUserInfo.getUserId());
        } catch (Exception e) {
            throw new BusinessException("处理解析错误",e);
            // 处理解析错误
        }

        Integer count = userMessageService.findCountByParam(userMessageQuery);
        return getSuccessResponseVO(count);
    }



    //分组获取未读消息数量
    @RequestMapping("/getNoReadCountGroup")
    public ResponseVO getNoReadCountGroup(HttpServletRequest request) throws BusinessException{
        Integer []count = {0,0};
        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setReadType(MessageReadTypeEnum.NO_READ.getType());

        try {
            //获取用户对象
            String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);
            //设置用户ID
            userMessageQuery.setUserId(claimsOfUserInfo.getUserId());
        } catch (Exception e) {
            throw new BusinessException("处理解析错误",e);
            // 处理解析错误
        }
        //获取未读系统消息数量
        userMessageQuery.setMessageType(1);
        count[0] = userMessageService.findCountByParam(userMessageQuery);
        //获取未读评论消息数量
        userMessageQuery.setMessageType(2);
        count[1] = userMessageService.findCountByParam(userMessageQuery);
        return getSuccessResponseVO(count);
    }



    //获取所有消息
    @RequestMapping("/readAll")
    public ResponseVO readAll(Integer pageNo ,HttpServletRequest request) throws BusinessException {

        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setPageNo(pageNo);
        userMessageQuery.setPageSize(Constants.LENGTH_15);
        try {
            //获取用户对象
            String redisUserInfoKey = CookieUtils.getCookie(request,Constants.TOKEN_KEY);
            ClaimsOfUserInfo claimsOfUserInfo = userInfoService.getByTokenOfUser(redisUserInfoKey);
            //设置用户ID
            userMessageQuery.setUserId(claimsOfUserInfo.getUserId());
        } catch (Exception e) {
            throw new BusinessException("处理解析错误",e);
            // 处理解析错误
        }
        PaginationResultVO<UserMessage> resultVO = userMessageService.findByPage(userMessageQuery);
        //遍历结果集，将消息标记为已读
        if (resultVO != null && !resultVO.getList().isEmpty()) {
            for (UserMessage message : resultVO.getList()) {
                // 将消息标记为已读
                message.setReadType(1);
                // 保存更新
                userMessageService.updateByMessageId(message, message.getMessageId());
            }
        }
        return getSuccessResponseVO(resultVO);
    }
}
