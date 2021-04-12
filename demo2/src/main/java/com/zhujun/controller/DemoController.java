package com.zhujun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2020/8/13 14:55
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2020
 **/

@RestController
public class DemoController {

    @GetMapping("/app/api")
    public String appApi(){
        return "hello app";
    }

    @GetMapping("/user/api")
    public String userApi(){
        return "hello user";
    }

    @GetMapping("/admin/api")
    public String adminApi(){
        return "hello admin";
    }


}
