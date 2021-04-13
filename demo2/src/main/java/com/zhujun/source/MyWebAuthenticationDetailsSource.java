package com.zhujun.source;

import com.zhujun.authentication.MyWebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyWebAuthenticationDetailsSource  extends WebAuthenticationDetailsSource {

    public MyWebAuthenticationDetailsSource() {
    }

    //传入servlet 生成  MyWebAuthenticationDetails
    public MyWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyWebAuthenticationDetails(context);
    }
}
