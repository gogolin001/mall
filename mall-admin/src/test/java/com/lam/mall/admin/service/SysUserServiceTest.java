package com.lam.mall.admin.service;

import com.lam.mall.admin.dto.UserParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootTest
//@Transactional
class SysUserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceTest.class);
    @Autowired
    private SysUserService userService;

    @DisplayName("测试用户注册")
    @Test
    @Rollback
    void register() {
        var result = userService.register(
                UserParam.builder().username("admin").password("admin@123").email("admin@mall.com").nickName("超管").roleIds("1").build()
        );
    }

    @Test
    void login() {
    }
}
