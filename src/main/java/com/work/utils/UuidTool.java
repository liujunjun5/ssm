package com.work.utils;
import java.util.UUID;
public class UuidTool {
    public  String generateUniqueOrderId() {
        // 生成一个UUID并转换为字符串，去掉连字符
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        // 截取前20个字符
        return uniqueId.substring(0, 20);//随机生成一个唯一的20位字符串
    }

    public  String generateUniqueProductId() {
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        return uniqueId.substring(0, 10);//随机生成一个唯一的10位字符串
    }
}