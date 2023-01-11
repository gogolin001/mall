package com.lam.mall.common.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.lam.mall.common.security.SMAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public Algorithm jwtAlgorithm() {
        return new SMAlgorithm();
    }

}
