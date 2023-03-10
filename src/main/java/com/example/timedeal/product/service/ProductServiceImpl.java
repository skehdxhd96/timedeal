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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void remove(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.validatedOnEvent();

        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductSelectResponse update(User currentUser, Long id, ProductUpdateRequest request) {

        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Product newProduct = ProductAssembler.product(currentUser, request, id);

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
    public List<ProductSelectResponse> findAllProducts(String eventName, Pageable pageable) {

        productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#eventName+#pageable.pageNumber",
                value = "eventProduct")
    public List<ProductSelectResponse> findAllProductsOnEvent(String eventName, Pageable pageable) {

    }

    @Override
    @Transactional
    public void assignEvent(Long id, ProductEventRequest request) {

        boolean isExists = productEventRepository.
                existsByProductIdAndPublishEventId(id, request.getPublishEventId());

        if(isExists) {
            throw new BusinessException(ErrorCode.ALREADY_HAS_EVENT);
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        PublishEvent publishEvent = publishEventRepository.findById(request.getPublishEventId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PUBLISH_NOT_YET));

        publishEvent.register(product); // 안돼면 Cascade 옵션 우선 고려해보자.
    }

    @Override
    @Transactional
    public void terminateEvent(Long id, ProductEventRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.terminateEvent(); // CASACDE로 해결 되나 ?

        productEventRepository.deleteById(request.getPublishEventId());
    }
}
