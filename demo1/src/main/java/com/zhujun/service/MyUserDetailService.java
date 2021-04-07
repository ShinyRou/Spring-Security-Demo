package com.zhujun.service;

import com.zhujun.dao.UserDao;
import com.zhujun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于自定义数据库模型 实现
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询用户信息
        User user = userDao.getUserByUserName(s);
        if(user == null){
            throw new UsernameNotFoundException("该用户不存在");
        }
        //根据username查询角色权限
        String authString = userDao.getAuthByUserName(s);
        //自动将权限按字符串字符“,”生成List<GrantedAuthority>
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authString));
        return user;
    }
}
