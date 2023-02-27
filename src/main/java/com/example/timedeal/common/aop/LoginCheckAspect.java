package com.example.timedeal.common.aop;

import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final UserService userService;

    @Before("@annotation(com.example.timedeal.common.annotation.LoginCheck)")
    public void LoginCheck() {

        // TODO : 키가 있는지만 확인한다.
    }

    @Before("@annotation(com.example.timedeal.common.annotation.ConsumerCheck)")
    public void ConsumerCheck() {

        // TODO : 키가 있는지 확인하고, 해당 키의 UserType이 Consumer인지 확인한다.
    }

    @Before("@annotation(com.example.timedeal.common.annotation.AdminCheck)")
    public void AdminCheck() {

        // TODO : 키가 있는지 확인하고, 해당 키의 UserType이 Administrator인지 확인한다.
    }
}
