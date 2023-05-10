package com.example.timedeal.user.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.dto.*;
import com.example.timedeal.user.entity.User;
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

    @LoginCheck
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@CurrentUser User currentUser) {

        loginService.logOut();
        userService.deleteMember(currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    @LoginCheck
    @GetMapping("/myPage")
    public ResponseEntity<UserSelectResponse> findMember(@CurrentUser User currentUser) {

        UserSelectResponse userResponse = userService.findMember(currentUser);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserSelectResponse> logIn(@Valid @RequestBody UserLoginRequest request) {
        UserSelectResponse loginUser = loginService.logIn(request);
        return ResponseEntity.ok(loginUser);
    }

    @LoginCheck
    @PostMapping("/logout")
    public ResponseEntity<Void> logOut() {
        loginService.logOut();
        return ResponseEntity.noContent().build();
    }

    // 특정 유저의 구매한 상품리스트
}
