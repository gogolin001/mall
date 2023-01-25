package com.lam.mall.common.security;

import cn.hutool.crypto.SmUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Sm3密码加密
 */
public class Sm3PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        return hashpw(rawPassword.toString(),getSalt());
    }

    /**
     * 获取盐值
     * @return
     */
    private String getSalt() {
        return BCrypt.gensalt("$2a", 10);
    }

    /**
     * 获取密码加盐摘要 （盐值 + SM3 (password+盐值)）
     * @param password 密码
     * @param salt 盐值
     * @return
     */
    private String hashpw(String password, String salt){
        return salt+SmUtil.sm3(password+salt);
    }

    /**
     * 验证密码加盐摘要之后是否一致
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = encodedPassword.toString().substring(0,29);
        return hashpw(rawPassword.toString(),salt).equals(encodedPassword);
    }
}
