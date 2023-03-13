package com.example.timedeal.product.service;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.repository.PublishEventRepository;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.repository.ProductEventRepository;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductEventRepository productEventRepository;
    private final PublishEventRepository publishEventRepository;

    @Override
    @Transactional
    public Long register(User currentUser, ProductSaveRequest request) {

        Product product = ProductAssembler.product(currentUser, request);

        Product newProduct = productRepository.save(product);

        return newProduct.getId();
    }

    @Override
    @Transactional
    public void remove(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.validateOnEvent();

        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductSelectResponse update(User currentUser, Long productId, ProductUpdateRequest request) {

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Product newProduct = ProductAssembler.product(currentUser, request, productId);

        findProduct.update(newProduct);

        return ProductSelectResponse.of(newProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSelectResponse findDetails(Long id) {

        Product product = productRepository.findProductDetailById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductSelectResponse.of(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSelectResponse> findAllProducts(Pageable pageable, ProductSearchRequest searchRequest) {

        return productRepository.findAllProducts(pageable, searchRequest).map(ProductSelectResponse::of);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#eventName+#pageable.pageNumber",
                value = "eventProduct") // eventProduct : {#eventName+#pageNumber : xxx, #eventName+#pageNumber, yyy}
    public Page<ProductSelectResponse> findAllProductsOnEvent(Pageable pageable, String eventName) {

        return productRepository.findAllProductOnEvent(pageable, eventName).map(ProductSelectResponse::of);
    }

    @Override
    @Transactional
    @CacheEvict(value = "eventProduct", )
    public void assignEvent(Long productId, ProductEventRequest request) {

        boolean isExists = productEventRepository.
                existsByProductIdAndPublishEventId(productId, request.getPublishEventId());

        if(isExists) {
            throw new BusinessException(ErrorCode.ALREADY_HAS_EVENT);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        PublishEvent publishEvent = publishEventRepository.findById(request.getPublishEventId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PUBLISH_NOT_YET));

        publishEvent.register(product);
    }

    @Override
    @Transactional
    public void terminateEvent(Long id, ProductEventRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        PublishEvent publishEvent = publishEventRepository.findById(request.getPublishEventId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PUBLISH_NOT_YET));

        publishEvent.terminate(product);
    }
}
