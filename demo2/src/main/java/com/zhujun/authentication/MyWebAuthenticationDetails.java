package com.zhujun.authentication;

import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    //验证码属性
    private String code;

    //存储在session中的code  使用后需要删除
    private String sessionCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }


    public String getSessionCode() {
        return sessionCode;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        //从request中获取 提交的code 与 session 中存储的code
        this.setCode(request.getParameter("code"));
        String sessionCode =(String) request.getSession().getAttribute("code");
        this.setSessionCode(sessionCode);
        if(StringUtils.isEmpty(sessionCode)){
            request.getSession().removeAttribute("code");
        }
    }
}
