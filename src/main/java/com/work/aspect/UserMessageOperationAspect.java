package com.work.aspect;

import com.work.annotation.RecordUserMessage;
import com.work.entity.po.ProductComment;
import com.work.entity.po.ProductInfo;
import com.work.entity.po.UserMessage;
import com.work.entity.vo.ResponseVO;

import com.work.enums.ResponseCodeEnum;
import com.work.exception.BusinessException;
import com.work.service.ProductInfoService;
import com.work.service.UserMessageService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

@Component
@Aspect
public class UserMessageOperationAspect {

    @Resource
    private UserMessageService userMessageService;
    @Resource
    private ProductInfoService productInfoService;

    //@annotation指明该表达式匹配方法级别的注解，即注解应放置于方法上
    //@within则将指明该表达式匹配类中的定义方法，即注解应放置于类上
    @Around("@annotation(com.work.annotation.RecordUserMessage)")
    public ResponseVO interceptorDo(ProceedingJoinPoint point) throws Exception {
        try {
            //执行被拦截方法，获取被拦截方法的返回结果
            ResponseVO result = (ResponseVO) point.proceed();
            //通过被拦截方法的方法签名获取Method对象
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            //获取被拦截方法所对应的RecordUserMessage的注解实例，有则返回实例，无则返回null
            RecordUserMessage recordUserMessage = method.getAnnotation(RecordUserMessage.class);
            //判断被拦截方法是否存在在注解实例，即是否使用该注解
            if ( recordUserMessage.messageType().getType() == 2 ) {
                //使用则执行saveUserMessage方法
                saveUserMessage(recordUserMessage,result);
            }
            if( recordUserMessage.messageType().getType() == 1 ){
                //使用则执行saveSysMessage方法
                saveSysMessage(recordUserMessage, point.getArgs(), method.getParameters());
            }
            return result;
        }  catch (Throwable e) {
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    /**
     * recordUserMessage
     * arguments         属性值
     * parameters        属性名
     */
    private void saveUserMessage(RecordUserMessage recordUserMessage,ResponseVO responseVO) {
        //获取评论对象
        ProductComment comment = (ProductComment) responseVO.getData();
        //判断是否为自我评论，如果是则不产生消息
        if(comment.getUserId() != comment.getProductUserId()) {
            //创建消息对象，获取消息参数
            UserMessage userMessage = getUserMessage(recordUserMessage, responseVO);
            //存入消息对象
            userMessageService.add(userMessage);
        }

    }

    private void saveSysMessage(RecordUserMessage recordUserMessage,Object[] arguments, Parameter[] parameters){
        String productId = null;
        for (int i = 0; i < parameters.length; i++) {
            if ("arg0".equals(parameters[i].getName())) {
                productId = (String) arguments[i];
            }
        }
        UserMessage userMessage = getSysMessage(recordUserMessage,productId);
        userMessageService.add(userMessage);
    }

    private UserMessage getUserMessage(RecordUserMessage recordUserMessage, ResponseVO responseVO) {
        //创建消息对象
        UserMessage userMessage = new UserMessage();
        //获取评论对象
        ProductComment comment = (ProductComment) responseVO.getData();

        //设置消息ID
        userMessage.setMessageId(userMessageService.findMaxMessageId()+1);
        //设置用户ID
        userMessage.setUserId(comment.getProductUserId());
        //设置商品号
        userMessage.setProductId(comment.getProductId());
        //设置发送人用户ID
        userMessage.setSendUserId(comment.getUserId());
        //设置消息类型
        userMessage.setMessageType(recordUserMessage.messageType().getType());
        //设置创建时间
        userMessage.setCreateTime(comment.getPostTime());
        //设置消息读阅状态，默认为未读
        userMessage.setReadType(0);
        //返回消息对象
        return userMessage;
    }

    private UserMessage getSysMessage(RecordUserMessage recordUserMessage, String productId){
        //创建消息对象
        UserMessage userMessage = new UserMessage();
        ProductInfo productInfo = productInfoService.getByProductId(productId);

        //设置消息ID
        userMessage.setMessageId(userMessageService.findMaxMessageId()+1);
        //设置用户ID
        userMessage.setUserId(productInfo.getProductUser());
        //设置商品号
        userMessage.setProductId(productId);
        //设置发送人用户ID
        userMessage.setSendUserId("管理员");
        //设置消息类型
        userMessage.setMessageType(recordUserMessage.messageType().getType());
        //设置创建时间
        userMessage.setCreateTime(new Date());
        //设置消息读阅状态，默认为未读
        userMessage.setReadType(0);
        //返回消息对象
        return userMessage;
    }


}
