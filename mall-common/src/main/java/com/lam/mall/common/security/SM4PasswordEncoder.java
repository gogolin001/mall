package com.lam.mall.common.security;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SM4PasswordEncoder implements PasswordEncoder {

    private final SymmetricCrypto sm4;

    public SM4PasswordEncoder(String key) {
        SymmetricCrypto crypto = SmUtil.sm4(key.getBytes());
        this.sm4 = crypto;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return sm4.encryptHex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return sm4.encryptHex(rawPassword.toString()).equals(encodedPassword);
    }
}
