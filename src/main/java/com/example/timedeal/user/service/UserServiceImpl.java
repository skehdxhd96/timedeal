package com.example.timedeal.user.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserSaveResponse joinMember(UserSaveRequest request) {

        validatedDuplicatedUserName(request.getUsername());

        // admin용은 하나 더 만드는게 맞겠다.
        Consumer user = Consumer.builder().userName(request.getUsername())
                .password(request.getPassword())
                .address(request.getAddress())
                .userType(UserType.CONSUMER)
                .build();

        Consumer saveUser = userRepository.save(user);

        return UserSaveResponse.of(saveUser);
    }

    @Transactional
    @Override
    public void deleteMember(Long userId) {

        // TODO : 구매에 성공해서 배달중인 데이터가 있다면 Exception
        // TODO : Select시 User로 검색하면 findById 2번나감

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public UserSelectResponse findMember(User user) {

        // TODO : 내가 구매한 상품 목록도 가져와야 함. => 이걸 여기서 한번에 가져오는게 좋은가 / 아니면 따로따로 API를 나누는게 좋은가?
        // TODO : API 나누고, Figma 그림에서 <내가 구매한 상품> 탭을 하나 만들어서 그 버튼을 눌렀을 때만 해당 API를 호출하도록 설계

        // TODO : 관리자용, 소비자용 selectDto 따로 있어야 됨.
        return UserSelectResponse.of(user);
    }

    private void validatedDuplicatedUserName(String username) {
        if(userRepository.existsByUserName(username)) {
            throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
        }
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
