package com.example.timedeal.user.controller;

import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.dto.*;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import static com.example.timedeal.user.service.SessionLoginService.USER_SESSION_KEY;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<UserSaveResponse> joinMember(@Valid @RequestBody UserSaveRequest request) {
        UserSaveResponse userResponse = userService.joinMember(request);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@SessionAttribute(name = USER_SESSION_KEY, required = false) AuthUser currentUser) {

        validateUserLogin(currentUser);

        loginService.logOut();
        userService.deleteMember(currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myPage")
    public ResponseEntity<UserSelectResponse> findMember(@SessionAttribute(name = USER_SESSION_KEY, required = false) AuthUser currentUser) {

        validateUserLogin(currentUser);

        UserSelectResponse userResponse = userService.findMember(currentUser.getId());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> logIn(@Valid @RequestBody UserLoginRequest request) {
        loginService.logIn(request);
        return ResponseEntity.noContent().build();
    }

    @LoginCheck
    @PostMapping("/logout")
    public ResponseEntity<Void> logOut() {
        loginService.logOut();
        return ResponseEntity.noContent().build();
    }

    private void validateUserLogin(AuthUser currentUser) {
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.LOG_IN_ESSENTIAL);
        }
    }
}
