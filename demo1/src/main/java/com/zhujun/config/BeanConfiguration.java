package com.zhujun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {

    @Autowired
    private DataSource dataSource;
    /**
     * 在内存中配置 账户密码角色
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager im = new InMemoryUserDetailsManager();
        im.createUser(User.withUsername("user2").password("user2").roles("USER").build());
        im.createUser(User.withUsername("admin2").password("admin2").roles("ADMIN").build());
        return im;
    }*/

    /*@Bean
    public UserDetailsService userDetailsService(){
      JdbcUserDetailsManager jm = new JdbcUserDetailsManager();
        jm.setDataSource(dataSource);
        if(jm.userExists("user2")){
            jm.createUser(User.withUsername("user2").password("user2").roles("USER").build());
        }
        if(jm.userExists("admin2")){
            jm.createUser(User.withUsername("admin2").password("admin2").roles("ADMIN").build());
        }

        return jm;
    }*/

    /**
     * 暂时设置密码编码器为NoOpPasswordEncoder5
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
