package com.example.timedeal.user.service;

import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;

public interface UserService extends SessionLoginService{

    public UserSaveResponse joinMember(UserSaveRequest request);
    public void deleteMember(Long id);
    public UserSelectResponse findMember(Long id);
}
