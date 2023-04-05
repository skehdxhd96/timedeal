package com.example.timedeal.user;

import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.timedeal.ApiDocumentUtils.getDocumentRequest;
import static com.example.timedeal.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
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

    @Test
    public void test() {
        System.out.println("test");
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

}
