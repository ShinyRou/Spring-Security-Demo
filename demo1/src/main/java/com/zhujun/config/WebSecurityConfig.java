package com.zhujun.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义表单 配置
        /*      http.
                authorizeRequests().
                antMatchers("/js/**","/css/**").permitAll(). //不拦截静态文件
                anyRequest()). //所有请求  如果是外置的css 与 js 需要修改 否则静态资源也会被拦截
                authenticated().and().
                formLogin().loginPage("/login.html").permitAll().//自定义登陆页面  登录页不设限访问
                //successHandler、failureHandler 指定登陆成功、失败的逻辑
                and().
                csrf().disable();//关闭 跨站请求伪造防护功能*/

        //认证授权配置
                http.
                authorizeRequests().
                antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/user/**").hasRole("USER").
                antMatchers("/app/**").permitAll().
                anyRequest(). //所有请求  如果是外置的css 与 js 需要修改 否则静态资源也会被拦截
                authenticated().and().
                formLogin().permitAll().and().
                csrf().disable();//关闭 跨站请求伪造防护功能
    }
}
