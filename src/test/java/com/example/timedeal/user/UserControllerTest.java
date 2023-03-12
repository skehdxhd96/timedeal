package com.example.timedeal.user;

import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.timedeal.ApiDocumentUtils.getDocumentRequest;
import static com.example.timedeal.ApiDocumentUtils.getDocumentResponse;
import static com.example.timedeal.user.service.SessionLoginService.USER_SESSION_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTest {

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

    @Test
    public void test() {
        System.out.println("test");
    }

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

//    @Test
//    @DisplayName("회원 로그인 테스트 - 성공")
//    void login_member_test() throws Exception {
//
//        UserLoginRequest userLoginRequest = UserFactory.userLoginRequest();
//        User loginUser = Consumer.builder()
//                .userName("test")
//                .password("1234")
//                .build();
//
//        //given
//
//        //when
//        ResultActions perform = mvc.perform(post("/api/v1/user/login")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(userLoginRequest)));
//
//        //then
//        verify(loginService, times(1)).logIn(userLoginRequest);
//    }

//    @Test
//    @DisplayName("회원 삭제 테스트")
//    void delete_member_test() throws Exception {
//
//        User loginUser = Consumer.builder()
//                .userName("test")
//                .password("1234")
//                .build();
//
//        AuthUser sessionUser = AuthUser.of(loginUser);
//
//        httpSession.setAttribute(USER_SESSION_KEY ,sessionUser);
//
//        mvc.perform(delete("/api/v1/user")
//                )
//    }

    @Test
    @DisplayName("마이페이지 조회 테스트")
    void find_member_test() {

    }

    @Test
    @DisplayName("회원 로그아웃 테스트")
    void logout_member_test() {

    }
}
