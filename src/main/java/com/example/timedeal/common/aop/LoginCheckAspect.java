package com.example.timedeal.common.aop;

import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;

    @Before("@annotation(com.example.timedeal.common.annotation.LoginCheck) && @annotation(target)")
    public void LoginCheck(LoginCheck target) {
        AuthUser currentUser = loginService.getCurrentUser();

        if(StringUtils.equals(target.role().name(), "GENERAL")) {
            checkLogin(currentUser);
        }

        else if(StringUtils.equals(target.role().name(), "ADMINISTRATOR")) {
            checkAdmin(currentUser);
        }

    }

    private void checkAdmin(AuthUser currentUser) {

        if(currentUser == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }

        if(!currentUser.getUsertype().equals("ADMINISTRATOR")) {
            throw new BusinessException(ErrorCode.ADMINISTRATOR_ONLY);
        }
    }

    private void checkLogin(AuthUser currentUser) {
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }
    }
}
