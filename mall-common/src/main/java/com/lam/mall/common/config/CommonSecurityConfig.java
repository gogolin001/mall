package com.lam.mall.common.config;

import cn.hutool.crypto.asymmetric.SM2;
import com.auth0.jwt.algorithms.Algorithm;
import com.lam.mall.common.security.*;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    public JwtProperties jwtProperties(){
        return new JwtProperties();
    }
    /**
     * jwt Algorithm
     * @return
     */
    @Bean
    public Algorithm jwtAlgorithm() {
        return new SMAlgorithm(jwtProperties());
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
        return new JwtHelper(jwtAlgorithm(),jwtProperties());
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