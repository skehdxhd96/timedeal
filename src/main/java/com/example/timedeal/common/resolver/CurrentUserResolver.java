package com.example.timedeal.common.resolver;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.repository.UserRepository;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final LoginService loginService;
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        AuthUser currentUser = loginService.getCurrentUser();

        if(currentUser == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }

        return userService.findUser(currentUser.getId());
    }
}
