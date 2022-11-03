package com.lam.mall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Indexed;

@Indexed
@MapperScan("com.lam.mall.mbg.mapper")
@SpringBootApplication(scanBasePackages = {"com.lam.mall.common","com.lam.mall.mbg","com.lam.mall.admin"})
public class MallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }
}
