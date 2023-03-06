package com.example.timedeal.product.service;

import com.example.timedeal.product.dto.ProductDtoAssembler;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
