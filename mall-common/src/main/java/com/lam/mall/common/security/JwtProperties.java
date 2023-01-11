package com.lam.mall.common.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties {
    /**
     * 秘钥
     */
    private String secret;
    /**
     * 有效时长（单位秒,默认1小时）
     */
    private Long expiration;
    /**
     * tokent头
     */
    private String tokenHead;
    /**
     * 发布者
     */
    private String issuer;
    /**
     * JWT 允许的时间误差。默认 180 。单位：秒。不同服务器的时间可能出现一些偏差，在该误差范围内认为是合理的。一般误差在3分钟内。
     */
    private Long leeway;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;
}
