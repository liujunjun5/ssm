package com.work.utils;

import org.springframework.util.StringUtils;
import com.work.entity.constants.Constants;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 前端传参检验类
 */
public class ParamCheckUtils {

    private static String Msg;

    public static String checkLoginParam(String email, String password, String checkCode){
        Msg = null;

        if(email.isEmpty()){
            Msg = "账号或邮箱不能为空";
        }else if(password.isEmpty()){
            Msg = "请输入密码";
        }else if(checkCode.isEmpty()){
            Msg = "请输入验证码";
        }

        return Msg;
    }
    public static String checkRegisterParam(String email, String password, String checkCode){

        if(checkLoginParam(email,password,checkCode)!=null){
            return checkLoginParam(email,password,checkCode);
        }

        Msg = null;
        Pattern patEmail = Pattern.compile(Constants.REGEX_EMAIL);
        Matcher matEmail = patEmail.matcher(email);
        Pattern patPassword = Pattern.compile(Constants.REGEX_PASSWORD);
        Matcher matPassword = patPassword.matcher(password);
        if (!matEmail.matches()){
            Msg="邮箱格式错误";
        }else if(!matPassword.matches()){
            Msg="密码格式错误";
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
