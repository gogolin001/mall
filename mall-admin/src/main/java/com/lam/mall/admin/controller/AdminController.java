package com.lam.mall.admin.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping("/index")
    public String Index(){
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
        String publicKey = Base64.encode(pair.getPublic().getEncoded());

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
// 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        return "hello world!";
    }
}
