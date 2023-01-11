package com.lam.mall.common.config;

import com.lam.mall.common.security.JwtHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jwt2Config {
    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper();
    }
}
