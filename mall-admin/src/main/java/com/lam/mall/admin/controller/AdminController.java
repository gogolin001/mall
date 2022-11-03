package com.lam.mall.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/index")
    public String Index(){
        return "hello world!";
    }

    @GetMapping("/login")
    public String Login(){
        return "login";
    }

}
