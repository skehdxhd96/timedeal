package com.example.timedeal.user.repository;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @DisplayName("아이디 중복검사에 성공한다.")
    @Test
    void 아이디_중복검사_테스트() {

        //given
        Consumer consumer = Consumer.builder()
                .id(1L)
                .userType(UserType.CONSUMER)
                .address("korea")
                .userName("test")
                .password("1234")
                .build();

        userRepository.saveAndFlush(consumer);

        //when
        boolean result = userRepository.existsByUserName("test");

        //then
        assertThat(result).isEqualTo(true);
    }

    @DisplayName("")
    @Test
    void 아이디_비밀번호로_유저찾기_테스트() {

        //given
        Consumer consumer = Consumer.builder()
                .id(1L)
                .userType(UserType.CONSUMER)
                .address("korea")
                .userName("test")
                .password("1234")
                .build();

        userRepository.saveAndFlush(consumer);

        //when
        User result = userRepository.findByUserNameAndPassword("test", "1234")
                .orElseThrow(NoSuchElementException::new);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result)
                .extracting("userName", "password", "userType")
                .contains("test", "1234", UserType.CONSUMER);
    }
}
