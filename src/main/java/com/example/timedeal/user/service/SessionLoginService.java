package com.example.timedeal.user.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final UserRepository userRepository;

    @Override
    public void logIn(UserLoginRequest request) {

        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        validateEqualUserNameAndPassword(user, request);

        // TODO : 세션값을 생성해 Redis에 집어넣는다. Key - value : 세션키 - {Id, UserType}, 자료구조는 해시.
    }

    @Override
    public void logOut() {
        // TODO : 세션값을 삭제한다.
    }

    private void validateEqualUserNameAndPassword(User user, UserLoginRequest request) {

        if(!user.getUserName().equals(request.getUsername())
                || !user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ErrorCode.LOG_IN_FAILURE);
        }
    }
}
