package com.example.timedeal.common.aop;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;

    @Before("@annotation(com.example.timedeal.common.annotation.LoginCheck)")
    public void LoginCheck() {
        String userType = loginService.getCurrentLoginType();

        if(userType == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }
    }

    @Before("@annotation(com.example.timedeal.common.annotation.AdminCheck)")
    public void AdminCheck() {

        String userType = loginService.getCurrentLoginType();

        if(userType == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }

        if(userType.equals("ADMINISTRATOR")) {
            throw new BusinessException(ErrorCode.ADMINISTRATOR_ONLY);
        }
    }
}
