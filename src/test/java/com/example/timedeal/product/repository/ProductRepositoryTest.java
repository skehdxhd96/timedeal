package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("상품 아이디로 상품 상세를 조회한다.")
    @Test
    void 아이디로_상품상세_검색하기() {

        //given
        Administrator administrator = Administrator.builder()
                .userName("test")
                .userType(UserType.ADMINISTRATOR)
                .password("1234")
                .build();
        userRepository.saveAndFlush(administrator);

        Product product = Product.builder()
                .createdBy(administrator)
                .productName("test product 1")
                .productPrice(10000)
                .description("test product 1 desc")
                .totalStockQuantity(100)
                .build();
        productRepository.saveAndFlush(product);

        //when
        Product result = productRepository.findProductDetailById(product.getId())
                .orElseThrow(NoSuchElementException::new);

        //then
        assertThat(result).isEqualTo(product);
    }

    @DisplayName("")
    @Test
    void 이벤트_상품_리스트_조회_테스트() {
        //given

        //when

        //then
    }

    @DisplayName("")
    @Test
    void 모든_상품_리스트_조회_테스트() {

        //given
        Administrator administrator = Administrator.builder()
                .userName("test")
                .userType(UserType.ADMINISTRATOR)
                .password("1234")
                .build();
        userRepository.saveAndFlush(administrator);

        List<Product> products = new ArrayList<>();

        Product product = Product.builder()
                .createdBy(administrator)
                .productName("test product 1")
                .productPrice(10000)
                .description("test product 1 desc")
                .totalStockQuantity(100)
                .build();

        Product product2 = Product.builder()
                .createdBy(administrator)
                .productName("test product 2")
                .productPrice(20000)
                .description("test product 2 desc")
                .totalStockQuantity(100)
                .build();

        Product product3 = Product.builder()
                .createdBy(administrator)
                .productName("test product 3")
                .productPrice(30000)
                .description("test product 3 desc")
                .totalStockQuantity(100)
                .build();

        products.add(product);
        products.add(product2);
        products.add(product3);

        productRepository.saveAllAndFlush(products);

        //when
//        productRepository.findAllProducts(PageRequest.of(0,2), new ProductSearchRequest("test", ))

        //then
    }

    @DisplayName("")
    @Test
    void 상품_상세_리스트_조회_테스트() {
        //given
        Administrator administrator = Administrator.builder()
                .userName("test")
                .userType(UserType.ADMINISTRATOR)
                .password("1234")
                .build();
        userRepository.saveAndFlush(administrator);

        List<Product> products = new ArrayList<>();

        Product product = Product.builder()
                .createdBy(administrator)
                .productName("test product 1")
                .productPrice(10000)
                .description("test product 1 desc")
                .totalStockQuantity(100)
                .build();

        Product product2 = Product.builder()
                .createdBy(administrator)
                .productName("test product 2")
                .productPrice(20000)
                .description("test product 2 desc")
                .totalStockQuantity(100)
                .build();

        Product product3 = Product.builder()
                .createdBy(administrator)
                .productName("test product 3")
                .productPrice(30000)
                .description("test product 3 desc")
                .totalStockQuantity(100)
                .build();

        products.add(product);
        products.add(product2);
        products.add(product3);

        productRepository.saveAllAndFlush(products);

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(4L);

        //when
        List<Product> result = productRepository.findProductDetailByProductIds(ids);

        //then
        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("id", "productName", "productPrice")
                .contains(
                        tuple(1L, "test product 1", (double) 10000),
                        tuple(2L, "test product 2", (double) 20000)
                );
    }
}
