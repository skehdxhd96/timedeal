package com.example.timedeal.user.controller;

import com.example.timedeal.common.annotation.AdminCheck;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "/api/v1/user")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        userService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @LoginCheck
    @GetMapping("/{id}")
    public ResponseEntity<UserSelectResponse> findMember(@PathVariable Long id) {
        UserSelectResponse userResponse = userService.findMember(id);
        return ResponseEntity.ok(userResponse);
    }

    @AdminCheck
    @GetMapping("/all")
    public void findAllMembers() {

         // TODO : 최대한 부하가 안걸리게끔 리스트를 가져와야 함.
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
}
