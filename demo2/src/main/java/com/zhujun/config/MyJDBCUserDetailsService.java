package com.zhujun.config;

import com.zhujun.dao.UserDao;
import com.zhujun.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2020/9/2 10:23
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2020
 **/
@Slf4j
@Component
public class MyJDBCUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询用户信息
        User user = userDao.getUserByUserName(s);
        if(user == null){
            log.info("{}用户不存在",s);
            throw new UsernameNotFoundException("该用户不存在");
        }
        //根据username查询角色权限
        String authString = userDao.getAuthByUserName(s);
        //自动将权限按字符串字符“,”生成List<GrantedAuthority>
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authString));
        return user;
    }

}
