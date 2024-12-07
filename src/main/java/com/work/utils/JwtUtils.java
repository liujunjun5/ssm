package com.work.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**ljz
 * JWT工具类
 */
public class JwtUtils {

    private static String signKey = "emanon";
    private static Long expire = 43200000L;//

    /**
     * 生成JWT令牌
     * @param object JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Object object){

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claims = objectMapper.convertValue(object, Map.class);

        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}