package com.zhujun.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * Description: [认证异常处理handler]
 * </p>
 * Created on 2021/3/15 17:09
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2021
 **/
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//这里直接写自己的处理逻辑，比如下面这段代码
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter out=httpServletResponse.getWriter();
        String messge = StringUtils.isEmpty(e.getMessage())?"登录失败":e.getMessage();
        out.write(messge);
        out.close();
    }
}
