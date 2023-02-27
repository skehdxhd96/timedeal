package com.example.timedeal.user.controller;

import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 안해도 가능 => 어노테이션
    @PostMapping
    public ResponseEntity<UserSaveResponse> joinMember(@Valid @RequestBody UserSaveRequest request) {
        UserSaveResponse userResponse = userService.joinMember(request);
        return ResponseEntity.ok(userResponse);
    }

    // 로그인 안해도 가능 => 어노테이션
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        userService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // CONSUMER / ADMINISTRATOR일 떄만 가능 => 어노테이션
    @GetMapping("/{id}")
    public ResponseEntity<UserSelectResponse> findMember(@PathVariable Long id) {
        UserSelectResponse userResponse = userService.findMember(id);
        return ResponseEntity.ok(userResponse);
    }

    // ADMINISTRATOR일 떄만 가능 => 어노테이션
    @GetMapping("/all")
    public void findAllMembers() {

         // TODO : 최대한 부하가 안걸리게끔 리스트를 가져와야 함.
    }
}
