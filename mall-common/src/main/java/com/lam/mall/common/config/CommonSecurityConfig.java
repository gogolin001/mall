package com.lam.mall.common.config;

import cn.hutool.crypto.asymmetric.SM2;
import com.auth0.jwt.algorithms.Algorithm;
import com.lam.mall.common.security.*;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity通用配置
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 * Created by macro on 2022/5/20.
 */
@Configuration
public class CommonSecurityConfig {

    /**
     * 公钥
     */
    @Value("${jwt.publicKey:e2ee455e19eb29c21fba2206311ae1ffbe2b20fb08b4ae48ab03417fafd6fb97dda83eb0613ffcc168c9c6cd5253c3ca8fbc66ad6f8071712e24cd95b165879c2b69d84faf31919bea666cada611835f6c985152140333403fb1b75c3daa1457c233e0f2243d7c}")
    private String publicKeyStr;
    /**
     * 私钥
     */
    @Value("${jwt.privateKey:7b455ff81b444c7deaa388e9af11903c5c90986a423ea0e270b499d19c76988c}")
    private String privateKeyStr;

    @Bean
    public SM2 sm2(){
        return new SM2(publicKeyStr,privateKeyStr).setMode(SM2Engine.Mode.C1C2C3);
    }

    @Bean
    public Algorithm jwtAlgorithm() {
        return new SMAlgorithm();
    }

    /**
     * 密码明文加密方式配置（使用国密SM4）
     * @return SM3摘要加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SM3PasswordEncoder();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }


    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter(){
        return new DynamicSecurityFilter();
    }
}