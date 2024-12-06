package com.work.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.work.entity.po.ClaimsOfUserInfo;
import com.work.entity.po.UserInfo;
import jdk.internal.dynalink.beans.StaticClass;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 前端传参检验类
 */
public class ParamCheckUtils {

    private static String Msg;

    public static String checkLoginParam(String emailOrAccount, String password, String checkCode){
        Msg = null;
        if(emailOrAccount.equals("")){
            Msg = "账号或邮箱不能为空";
        }else if(password.equals("")){
            Msg = "请输入密码";
        }else if(checkCode.equals("")){
            Msg = "请输入验证码";
        }
        return Msg;
    }

    public static String areAllPropertiesEmpty(Object object) {
        Msg = null;
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段

            try {
                Object value = field.get(object);

                // 检查是否为null或者String类型的属性是否为空字符串
                if ( value instanceof String ){
                    if(StringUtils.hasLength((String) value)){
                        return Msg;
                    }else{
                        field.set(object, null);
                    }
                }else if(value != null) {
                    return Msg;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                // 你可以选择抛出异常或者记录日志，这里简单处理为打印堆栈跟踪
            }
        }
        Msg = "至少修改一项";
        return Msg;
    }

}
