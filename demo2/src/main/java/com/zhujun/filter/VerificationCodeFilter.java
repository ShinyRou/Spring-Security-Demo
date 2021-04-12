package com.zhujun.filter;

import com.zhujun.exception.VerificationCodeException;
import com.zhujun.handler.MyAuthenticationFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * Description: [验证码处理 过滤器]
 * </p>
 * Created on 2021/3/15 17:04
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2021
 **/
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler  authenticationFailureHandler =new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //除了登录页不做 验证码鉴定 此处省略
        if(!"/login".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            try{
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }catch(AuthenticationException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }
        }
    }

    private void verificationCode (HttpServletRequest httpServletRequest) throws  VerificationCodeException{
        String code = (String)httpServletRequest.getParameter("code");
        String saveCode = (String)httpServletRequest.getSession().getAttribute("code");
        if(StringUtils.isEmpty(saveCode)){
            httpServletRequest.getSession().removeAttribute("code");
        }
        if(StringUtils.isEmpty(code)){
            throw  new VerificationCodeException("请输入验证码");
        }
        if(!code.equals(saveCode)){
            throw  new VerificationCodeException("验证码不正确");
        }
    }
}
