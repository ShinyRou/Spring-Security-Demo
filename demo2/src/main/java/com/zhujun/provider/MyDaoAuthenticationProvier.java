package com.zhujun.provider;

import com.zhujun.authentication.MyWebAuthenticationDetails;
import com.zhujun.config.MyJDBCUserDetailsService;
import com.zhujun.exception.VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class MyDaoAuthenticationProvier  extends DaoAuthenticationProvider {

    public MyDaoAuthenticationProvier(MyJDBCUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //校验验证码
        MyWebAuthenticationDetails webAuthenticationDetails = (MyWebAuthenticationDetails)authentication.getDetails();
        String code = webAuthenticationDetails.getCode();
        String sessionCode= webAuthenticationDetails.getSessionCode();
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(sessionCode)){
            throw new VerificationCodeException("验证码验证失败");
        }
        super.additionalAuthenticationChecks(userDetails,authentication);
    }
}
