package com.example.timedeal.user.service;

import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;

public interface UserService {

    UserSaveResponse joinMember(UserSaveRequest request);
    void deleteMember(Long id);
    UserSelectResponse findMember(Long id);
}
