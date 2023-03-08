package com.example.timedeal.user.service;

import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;

public interface UserService {

    UserSaveResponse joinMember(UserSaveRequest request);
    void deleteMember(User user);
    UserSelectResponse findMember(User user);
}
