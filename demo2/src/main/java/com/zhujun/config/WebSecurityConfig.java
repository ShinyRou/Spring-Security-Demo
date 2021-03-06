package com.zhujun.config;

import com.zhujun.authentication.MyWebAuthenticationDetails;
import com.zhujun.filter.VerificationCodeFilter;
import com.zhujun.provider.MyDaoAuthenticationProvier;
import com.zhujun.source.MyWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2020/8/13 14:54
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2020
 **/
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //注入自定义的detailSource
    @Autowired
    private MyWebAuthenticationDetailsSource myWebAuthenticationDetailsSource;

    //注入自定义的AuthenticationProvier
    @Autowired
    private MyDaoAuthenticationProvier myDaoAuthenticationProvier;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //应用自定义的AuthenticationProvier
        auth.authenticationProvider(myDaoAuthenticationProvier);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                 http.
                authorizeRequests().
                antMatchers("/admin/**").hasRole("ADMIN").//需要admin 角色
                antMatchers("/user/**").hasRole("USER").
                antMatchers("/app/**","/verifiedCode/**","/js/**","/css/**").permitAll().
                anyRequest().authenticated()
                .and().
                         //应用自定义的AuthenticationDetailsSource
                formLogin().authenticationDetailsSource(myWebAuthenticationDetailsSource)
                         .loginPage("/login.html").loginProcessingUrl("/login").permitAll()//自定义登陆页面  登录页不设限访问
                .and().
                csrf().disable();


/*              注册过滤器
                http.addFilterBefore(new VerificationCodeFilter(),UsernamePasswordAuthenticationFilter.class);
*/
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    //内存配置 用户信息
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception  {
        auth.inMemoryAuthentication().
                withUser("user").password("{noop}user").roles("USER").and().
                withUser("admin").password("{noop}admin").roles("ADMIN");
    }*/
}