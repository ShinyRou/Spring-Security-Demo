package com.zhujun.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 * Description: [验证码异常]
 * </p>
 * Created on 2021/3/15 17:02
 *
 * @author <a href="mailto: "></a> zhujun
 * @version 1.0
 * Copyright (c) 2021
 **/
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException() {
        super("图形校验验证码失败");
    }

    public VerificationCodeException(String explanation) {
        super(explanation);
    }
}
