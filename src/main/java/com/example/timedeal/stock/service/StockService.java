package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockHistoryRepository stockHistoryRepository;
    private final ProductService productService;
    private final StockOperation stockOperation;

    /* 물건을 산다. */
    @Transactional
    public void decrease(Long productId, User user) {

        Product product = productService.findDetails(productId);

        /* 이벤트인 경우 / 일반 상품인 경우 => 이벤트인 경우 시간/재고 체크, 일반상품인 경우 재고 체크 ( 유효성 체크 )*/


        /* 재고 감소 */

        /* 히스토리 추가 */
//        stockHistoryRepository.save()
    }

    // 롤백로직
}
