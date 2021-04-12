package com.zhujun.dao;


import com.zhujun.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    @Select("select * from users where username = #{username}")
    public User getUserByUserName(String username);

    @Select("select authority from authorities where username = #{username}")
    public String getAuthByUserName(String username);
}
