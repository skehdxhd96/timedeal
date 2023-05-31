package com.example.timedeal.user.controller;

import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.timedeal.ApiDocumentUtils.getDocumentRequest;
import static com.example.timedeal.ApiDocumentUtils.getDocumentResponse;
import static com.example.timedeal.user.service.SessionLoginService.USER_SESSION_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

    public static final String USER_SESSION_KEY = "USER_ID";
    @Autowired
    private MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private MockHttpSession httpSession;
    @AfterEach
    void clearSession() {
        httpSession.clearAttributes();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void join_member_test() throws Exception {

        UserSaveRequest userSaveRequest = UserFactory.userSaveRequest();
        UserSaveResponse userSaveResponse = UserFactory.userSaveResponse();

        //given
        given(userService.joinMember(any(UserSaveRequest.class)))
                .willReturn(userSaveResponse);

        //when
        ResultActions perform = mvc.perform(post("/api/v1/user")
                        .content(objectMapper.writeValueAsString(userSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        String body = perform
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(objectMapper.writeValueAsString(userSaveResponse));

        verify(userService, times(1))
                .joinMember(any(UserSaveRequest.class));

        perform.andDo(document("join-member",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                ),
                requestFields(
                        fieldWithPath("username").type(STRING).description("test"),
                        fieldWithPath("password").type(STRING).description("1234"),
                        fieldWithPath("address").type(STRING).description("korea")
                ),
                responseFields(
                        fieldWithPath("id").type(LONG).description(1L),
                        fieldWithPath("username").type(STRING).description("test")
                )
        ));
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void delete_member_test() throws Exception {

        Consumer consumer = UserFactory.consumer();
        UserSelectResponse userSelectResponse = UserFactory.userSelectResponse();

        // given
        given(userService.findUser(any(Long.class)))
                .willReturn(consumer);
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(consumer));
        given(loginService.logIn(any(UserLoginRequest.class)))
                .willReturn(userSelectResponse);

        // when
        ResultActions perform = mvc.perform(delete("/api/v1/user")
                .header("COOKIE", httpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL));

        // then
        perform.andExpect(status().isNoContent());
        verify(loginService, times(1))
                .logOut();
        verify(userService, times(1))
                .deleteMember(any());

        // restdocs
        perform.andDo(document("delete-member",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                )));
    }

    @Test
    @DisplayName("마이페이지 조회 테스트")
    void find_member_test() throws Exception {

        UserSelectResponse userSelectResponse = UserFactory.userSelectResponse();
        Consumer consumer = UserFactory.consumer();

        // given
        given(userService.findUser(any(Long.class)))
                .willReturn(consumer);
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(consumer));
        given(userService.findMember(consumer))
                .willReturn(userSelectResponse);

        //when
        ResultActions perform = mvc.perform(get("/api/v1/user/myPage")
                .header("COOKIE", httpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL));

        //then
        String returnBody = perform.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(returnBody)
                .isEqualTo(objectMapper.writeValueAsString(userSelectResponse));
        verify(userService, times(1))
                .findMember(consumer);

        // restdocs
        perform.andDo(document("get-myPage",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                ),
                responseFields(
                        fieldWithPath("userId").type(NUMBER).description("Current Login User Id"),
                        fieldWithPath("userName").type(STRING).description("Current Login User Name"),
                        fieldWithPath("userType").type(STRING).description("Current Login User PW")
                )));
    }

    @Test
    @DisplayName("회원 로그인 테스트")
    void login_member_test() throws Exception {

        Consumer consumer = UserFactory.consumer();
        UserLoginRequest userLoginRequest = UserFactory.userLoginRequest();
        UserSelectResponse userSelectResponse = UserSelectResponse.of(AuthUser.of(consumer));

        // given
        given(loginService.logIn(any(UserLoginRequest.class)))
                .willReturn(userSelectResponse);

        // when
        ResultActions perform = mvc.perform(post("/api/v1/user/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL));

        // then
        String returnBody = perform.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(loginService, times(1))
                .logIn(any(UserLoginRequest.class));

        assertThat(returnBody).isEqualTo(objectMapper.writeValueAsString(userSelectResponse));

        // restdocs
        perform.andDo(document("user-login",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                ),
                requestFields(
                        fieldWithPath("username").type(STRING).description("UserName"),
                        fieldWithPath("password").type(STRING).description("Password")
                )));
    }

    @Test
    @DisplayName("회원 로그아웃 테스트")
    void logout_member_test() throws Exception {

        Consumer consumer = UserFactory.consumer();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(consumer));

        // when
        ResultActions perform = mvc.perform(post("/api/v1/user/logout")
                                .header("COOKIE", httpSession)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL));

        // then
        perform.andExpect(status().isNoContent());
        verify(loginService, times(1))
                .logOut();

        // restdocs
        perform.andDo(document("user-logout",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                )));
    }
}
