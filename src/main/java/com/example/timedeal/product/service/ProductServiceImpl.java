package com.example.timedeal.product.service;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.repository.PublishEventRepository;
import com.example.timedeal.common.entity.RestPage;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.stock.service.StockOperation;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final PublishEventRepository publishEventRepository;
    private final StockOperation stockOperation;

    @Override
    @Transactional
    public Long register(User currentUser, ProductSaveRequest request) {

        Product product = ProductAssembler.product(currentUser, request);
        Product newProduct = productRepository.save(product);

        stockOperation.register(newProduct);

        return newProduct.getId();
    }

    @Override
    @Transactional
    public void remove(Long productId) {

        Product product = findProductById(productId);
        product.validateOnEvent();

        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductSelectResponse update(User currentUser, Long productId, ProductUpdateRequest request) {

        Product findProduct = findProductById(productId);
        Product newProduct = ProductAssembler.product(currentUser, request, productId);

        findProduct.update(newProduct);

        return ProductSelectResponse.of(newProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findDetails(Long productId) {

        return findProductDetailById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSelectResponse> findAllProducts(Pageable pageable, ProductSearchRequest searchRequest) {

        return productRepository.findAllProducts(pageable, searchRequest)
                                    .map(ProductSelectResponse::of);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#eventCode+':'+#pageable.pageNumber",
               cacheManager = "redisCacheManager",
               value = "eventProduct")
    public RestPage<ProductEventSelectResponse> findAllProductsOnEvent(Pageable pageable, String eventCode) {

        return new RestPage<>(productRepository.findAllProductOnEvent(pageable, eventCode)
                                    .map(ProductEventSelectResponse::of));
    }

    @Override
    @Transactional
    public void assignEvent(Long productId, ProductEventRequest request) {

        Product product = findProductById(productId);
        PublishEvent publishEvent = findPublishEventById(request.getPublishEventId());

        boolean isExists = publishEvent.getProductEvents()
                .contains(new ProductEvent(product, publishEvent));

        if(isExists)
            throw new BusinessException(ErrorCode.ALREADY_HAS_EVENT);

        publishEvent.register(product);
    }

    @Override
    @Transactional
    public void terminateEvent(Long productId, Long publishEventId) {

        Product product = findProductById(productId);
        PublishEvent publishEvent = findPublishEventById(publishEventId);

        publishEvent.terminate(product);
    }

    private Product findProductDetailById(Long productId) {
        return productRepository.findProductDetailById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private PublishEvent findPublishEventById(Long publishEventId) {
        return publishEventRepository.findById(publishEventId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PUBLISH_NOT_YET));
    }
}
