package com.example.timedeal.user.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    public static final String USER_SESSION_KEY = "USER_ID";
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public UserSelectResponse logIn(UserLoginRequest request) {

        User user = userRepository.findByUserNameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new BusinessException(ErrorCode.LOG_IN_FAILURE));

        httpSession.setAttribute(USER_SESSION_KEY, AuthUser.of(user));

        return UserSelectResponse.of(getCurrentUser());
    }

    @Override
    public void logOut() {
        httpSession.invalidate();
    }

    @Override
    public AuthUser getCurrentUser() {
        return (AuthUser) httpSession.getAttribute(USER_SESSION_KEY);
    }
}
