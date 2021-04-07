package com.zhujun.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/***
 *  对外开放 不需要认证授权
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @RequestMapping("/function")
    public String appFunction(){
        return "this is app function";
    }
}
