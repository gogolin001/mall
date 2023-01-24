package com.lam.mall.common.security;

import cn.hutool.crypto.SmUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sm3PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return SmUtil.sm3(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return SmUtil.sm3(rawPassword.toString()).equals(encodedPassword);
    }
}
