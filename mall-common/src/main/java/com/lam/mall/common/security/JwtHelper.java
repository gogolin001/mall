package com.lam.mall.common.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成jwt的工具类，基于auth0.java-jwt封装
 * 签名算法使用SM3WithSM2
 * payload统一使用Map<String, String>类型
 * @author Created by zkk on 2020/9/22
 **/
@Slf4j
public class JwtHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);

    @Autowired
    private SMAlgorithm smAlgorithm;

    /**
     * 秘钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 有效时长（单位秒,默认1小时）
     */
    @Value("${jwt.expiration:3600}")
    private Long expiration;
    /**
     * tokent头
     */
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 发布者
     */
    @Value("${jwt.issuer:lam}")
    private String issuer;


    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Map<String, com.auth0.jwt.interfaces.Claim> getClaimsFromToken(String token) {
        JWTVerifier verifier = JWT.require(smAlgorithm).withIssuer(issuer).build();
        DecodedJWT jwt =  verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(smAlgorithm).withIssuer(issuer).build();
        DecodedJWT jwt =  verifier.verify(token);
        return jwt;
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getDecodedJWT(token).getExpiresAt();
        return expiredDate.before(new Date());
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Date created = getDecodedJWT(token).getIssuedAt();
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        return refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time));
    }


    /**
     * 生成jwt
     * @param claims 携带的payload
     * @return jwt token
     */
    public String genToken(String username, Map<String, String> claims){
        try {
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(issuer) //token签发人
                    .withExpiresAt(generateExpirationDate()) //设置过期时间
                    .withIssuedAt(new Date())//设置证书发布时间
                    .withSubject(username) //用户名
                    ;

            claims.forEach(builder::withClaim);
            return builder.sign(smAlgorithm);
        } catch (IllegalArgumentException e) {
            log.error("jwt生成失败", e);
        }
        return null;
    }

    /**
     * 验签方法
     * @param token jwt token
     * @return jwt payload
     */
    public boolean verifyToken(String token, String username) {
        return username.equals(getUserNameFromToken(token)) && !isTokenExpired(token);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            username = getDecodedJWT(token).getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        if(StrUtil.isEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if(StrUtil.isEmpty(token)){
            return null;
        }
        //token校验不通过
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if(decodedJWT==null){
            return null;
        }
        //如果token已经过期，不支持刷新
        if(isTokenExpired(token)){
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if(tokenRefreshJustBefore(token,30*60)){
            return token;
        }else{
            return genToken(getUserNameFromToken(token),null);
        }
    }

    /**
     * 获取payload内容
     * @param token jwt token
     * @return jwt payload
     */
    public Map<String, String> verifyToken(String token) {
        DecodedJWT jwt =  getDecodedJWT(token);
        Map<String,  com.auth0.jwt.interfaces.Claim> map = jwt.getClaims();
        Map<String, String> resultMap = new HashMap<>();
        map.forEach((k,v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }
}
