package com.example.timedeal.product.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.ProductDtoAssembler;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Long register(User currentUser, ProductSaveRequest request) {

        Product product = ProductDtoAssembler.product(currentUser, request);

        Product newProduct = productRepository.save(product);

        return newProduct.getId();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductSelectResponse update(User currentUser, Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductSelectResponse();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSelectResponse findDetails(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductSelectResponse();
    }
}
