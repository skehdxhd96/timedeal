package com.example.timedeal.product;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.factory.EventFactory;
import com.example.timedeal.common.factory.ProductFactory;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.timedeal.ApiDocumentUtils.*;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.*;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;
    @MockBean
    private ProductService productService;
    @MockBean
    private MockHttpSession httpSession;
    private static MockedStatic<ProductAssembler> productAssembler;

    @BeforeAll
    public static void beforeALl() {
        productAssembler = mockStatic(ProductAssembler.class);
    }

    @AfterAll
    public static void afterAll() {
        productAssembler.close();
    }

    @AfterEach
    void clearSession() {
        httpSession.clearAttributes();
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void register_product_test() throws Exception {

        Administrator administrator = UserFactory.administrator();
        ProductSaveRequest productSaveRequest = ProductFactory.productSaveRequest();
        Product product = ProductFactory.product();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);
        given(productService.register(any(Administrator.class), any(ProductSaveRequest.class)))
                .willReturn(product.getId());

        // when
        ResultActions perform = mvc.perform(post("/api/v1/product")
                .header("COOKIE", httpSession)
                .content(objectMapper.writeValueAsString(productSaveRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isCreated());
        verify(productService, times(1))
                .register(any(Administrator.class), any(ProductSaveRequest.class));

        // restdocs
        perform.andDo(document("product-register",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                requestFields(
                        fieldWithPath("productName").type(STRING).description("product Name"),
                        fieldWithPath("productPrice").type(INTEGER).description("product Price"),
                        fieldWithPath("description").type(STRING).description("product Description"),
                        fieldWithPath("totalStockQuantity").type(INTEGER).description("product Stock Total Quantity")
                )));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete_product_test() throws Exception {

        Administrator administrator = UserFactory.administrator();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);
        willDoNothing()
                .given(productService)
                .remove(any(Long.class));

        // when
        ResultActions perform = mvc.perform(delete("/api/v1/product/{productId}", 1L)
                .header("COOKIE", httpSession));

        // then
        perform.andExpect(status().isNoContent());
        verify(productService, times(1))
                .remove(any(Long.class));

        // restdocs
        perform.andDo(document("remove-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                pathParameters(
                        parameterWithName("productId").description("product Id")
                )));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void update_product_test() throws Exception {

        Administrator administrator = UserFactory.administrator();
        ProductUpdateRequest productUpdateRequest = ProductFactory.productUpdateRequest();
        ProductSelectResponse productUpdateSelectResponse = ProductFactory.productUpdateSelectResponse();

        String expectedResult = objectMapper.writeValueAsString(productUpdateSelectResponse);

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);
        given(productService.update(any(Administrator.class), any(Long.class), any(ProductUpdateRequest.class)))
                .willReturn(productUpdateSelectResponse);

        // when
        ResultActions perform = mvc.perform(put("/api/v1/product/{productId}", 1L)
                .header("COOKIE", httpSession)
                .content(objectMapper.writeValueAsString(productUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isCreated())
                .andExpect(content().string(expectedResult));

        verify(productService, times(1))
                .update(any(Administrator.class), any(Long.class), any(ProductUpdateRequest.class));

        // restdocs
        perform.andDo(document("update-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                pathParameters(
                        parameterWithName("productId").description("product Id")
                ),
                requestFields(
                        fieldWithPath("productName").type(STRING).description("updated product name"),
                        fieldWithPath("productPrice").type(INTEGER).description("updated product price"),
                        fieldWithPath("description").type(STRING).description("updated product description")
                ),
                responseFields(
                        fieldWithPath("id").type(LONG).description("product id"),
                        fieldWithPath("productName").type(STRING).description("updated product name"),
                        fieldWithPath("productPrice").type(INTEGER).description("updated product price"),
                        fieldWithPath("description").type(STRING).description("updated product description")
                )
        ));
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void find_product_detail_test() throws Exception {

        Product product = ProductFactory.product();
        ProductSelectResponse productSelectResponse = ProductFactory.productSelectResponse();

        String expectedResult = objectMapper.writeValueAsString(productSelectResponse);

        // given
        given(productService.findDetails(any(Long.class)))
                .willReturn(product);

        // when
        ResultActions perform = mvc.perform(get("/api/v1/product/{productId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().string(expectedResult));

        verify(productService, times(1))
                .findDetails(any(Long.class));

        // restdocs
        perform.andDo(document("find-detail-product",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("productId").description("product id")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("product id"),
                        fieldWithPath("productName").type(STRING).description("product name"),
                        fieldWithPath("productPrice").type(NUMBER).description("product price"),
                        fieldWithPath("description").type(STRING).description("product description")
                )));
    }

    @Test
    @DisplayName("상품 리스트 조회 테스트")
    void find_product_list_test() {

        // given

        // when

        // then
    }

    @Test
    @DisplayName("상품 이벤트 할당 테스트")
    void assign_event_to_product_test() throws Exception {

        Administrator administrator = UserFactory.administrator();
        ProductEventRequest productEventRequest = ProductFactory.productEventRequest();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);
        willDoNothing()
                .given(productService)
                .assignEvent(any(Long.class), any(ProductEventRequest.class));

        // when
        ResultActions perform = mvc.perform(post("/api/v1/product/event/{productId}", 1L)
                .header("COOKIE", httpSession)
                .content(objectMapper.writeValueAsString(productEventRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isNoContent());
        verify(productService, times(1))
                .assignEvent(any(Long.class), any(ProductEventRequest.class));

        // restdocs
        perform.andDo(document("assign-event-to-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                pathParameters(
                        parameterWithName("productId").description("product id")
                ),
                requestFields(
                        fieldWithPath("publishEventId").type(NUMBER).description("publish event id")
                )));
    }

    @Test
    @DisplayName("상품 이벤트 해지 테스트")
    void terminate_event_to_product_test() throws Exception {

        Administrator administrator = UserFactory.administrator();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);
        willDoNothing()
                .given(productService)
                .terminateEvent(any(Long.class), any(Long.class));

        // when
        ResultActions perform = mvc.perform(delete("/api/v1/product/event/{productId}/terminate/{publishEventId}", 1L, 1L)
                                    .header("COOKIE", httpSession));

        // then
        perform.andExpect(status().isNoContent());
        verify(productService, times(1))
                .terminateEvent(any(Long.class), any(Long.class));

        // restdocs
        perform.andDo(document("terminate-event-from-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                pathParameters(
                        parameterWithName("productId").description("product id"),
                        parameterWithName("publishEventId").description("publish event id")
                )));
    }
}
