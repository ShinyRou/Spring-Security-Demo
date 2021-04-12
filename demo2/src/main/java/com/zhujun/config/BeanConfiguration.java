package com.zhujun.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2020/8/13 15:19
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2020
 **/
@Configuration
public class BeanConfiguration {

    //内存配置用户信息
    /*@Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager im = new InMemoryUserDetailsManager();
        im.createUser(User.withUsername("user2").password("user2").roles("USER").build());
        im.createUser(User.withUsername("admin2").password("admin2").roles("ADMIN").build());
        return im;
    }*/


    @Bean
    public Producer captcha(){
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kapcchar.image.width","150");
        //图片高度
        properties.setProperty("kapcchar.image.height","50");
        //字符集
        properties.setProperty("kapcchar.textproducer.char.string","0123456789");
        //验证长度
        properties.setProperty("kapcchar.textproducer.char.length","4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return  defaultKaptcha;
    }
}
