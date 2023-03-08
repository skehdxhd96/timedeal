package com.example.timedeal.product.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.ProductAssembler;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
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

        Product product = ProductAssembler.product(currentUser, request);

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

        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Product newProduct = ProductAssembler.product(currentUser, request, id);

        findProduct.update(newProduct);

        return new ProductSelectResponse(newProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSelectResponse findDetails(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 이벤트 데이터랑 같이 다 가져와야 함.

        return new ProductSelectResponse(product);
    }
}
