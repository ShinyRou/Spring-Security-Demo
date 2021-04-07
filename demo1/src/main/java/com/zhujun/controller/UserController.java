package com.zhujun.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 需要 user 用户权限
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/function")
    public String userFunction(){
        return "this is user function";
    }


}
