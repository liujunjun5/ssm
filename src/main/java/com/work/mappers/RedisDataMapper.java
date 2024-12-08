package com.work.mappers;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDataMapper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void deleteByKey(String key) {
        stringRedisTemplate.delete(key);
    }

    public void setData(String key,String value,Integer time){

        stringRedisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);

    }
    public void setData(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }


    public String getValueByKey(String key){

        return stringRedisTemplate.opsForValue().get(key);

    }

    public <T> T getByKey(String key, Class<T> clazz) {

        String json = stringRedisTemplate.opsForValue().get(key);

        return JSON.parseObject(json,clazz);
    }

    public <T> void updateByKey(String key,T object){

        String json = stringRedisTemplate.opsForValue().get(key);

        JSONObject jsonObject = JSON.parseObject(json);
        T obj = (T) jsonObject.toJavaObject(object.getClass()); // 注意这里使用了object.getClass()来获取类型

        // 使用反射来复制非空属性
        copyNonNullProperties(object, obj);

        // 将修改后的对象序列化为JSON字符串并存回Redis
        json = JSON.toJSONString(obj);
        stringRedisTemplate.opsForValue().set(key, json);

    }



    private void copyNonNullProperties(Object source, Object target) {

        Field[] sourceFields = source.getClass().getDeclaredFields();
        try {
            for (Field field : sourceFields) {
                field.setAccessible(true); // 允许访问私有字段
                Object value = field.get(source);
                if (value != null) {
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true); // 允许访问私有字段
                    // 检查字段类型是否兼容，这里简化处理为直接赋值（可能会抛出ClassCastException）
                    targetField.set(target, value);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            // 这里可以记录日志或抛出异常，根据实际需求处理
        }
    }
}
