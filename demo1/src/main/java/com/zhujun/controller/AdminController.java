package com.zhujun.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 需要admin 角色权限
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/function")
    public String adminFunction(){
        return "this is admin function";
    }
}
