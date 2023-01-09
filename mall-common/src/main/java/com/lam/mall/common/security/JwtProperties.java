package com.lam.mall.common.security;

import lombok.Builder;
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
    private String secret="9f$amuLu3JhU&3DVlx$t3mhsClBIVTiY";
    /**
     * 有效时长（单位秒,默认1小时）
     */
    private Long expiration=3600L;
    /**
     * tokent头
     */
    private String tokenHead="Bearer ";
    /**
     * 发布者
     */
    private String issuer="lam";
    /**
     * JWT 允许的时间误差。默认 180 。单位：秒。不同服务器的时间可能出现一些偏差，在该误差范围内认为是合理的。一般误差在3分钟内。
     */
    private Long leeway=180L;
    /**
     * 公钥
     */
    private String publicKey="e2ee455e19eb29c21fba2206311ae1ffbe2b20fb08b4ae48ab03417fafd6fb97dda83eb0613ffcc168c9c6cd5253c3ca8fbc66ad6f8071712e24cd95b165879c2b69d84faf31919bea666cada611835f6c985152140333403fb1b75c3daa1457c233e0f2243d7c";
    /**
     * 私钥
     */
    private String privateKey="7b455ff81b444c7deaa388e9af11903c5c90986a423ea0e270b499d19c76988c";
}
