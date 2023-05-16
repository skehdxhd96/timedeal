package com.example.timedeal.stock.service;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final ProductService productService;
    private final StockOperation stockOperation;

    /* 물건을 산다. */
    @Async
    @Transactional
    public void decrease(Long productId, User user) {

        Product product = productService.findDetails(productId);
        /* 이벤트인 경우 / 일반 상품인 경우 => 이벤트인 경우 시간/재고 체크, 일반상품인 경우 재고 체크 ( 유효성 체크 )*/

        /* 재고 감소 */

        /* 히스토리 추가 */
//        stockHistoryRepository.save()
    }

    // 롤백로직
    // Annotation 기반으로 Redis RollBack 로직을 작성할 수 있지만, 하지 않는다.
    // AOP를 이용해 파라미터를 받아와야 하며, 파라미터가 특정 클래스에 의존적이게 되어 해당 어노테이션을 사용하려면 반드시 해당 클래스를 상속받은 파라미터를 사용해야한다는 단점이 있기 때문
    // Object나 Generic을 사용해 파라미터 자체는 받아올 수 있지만, 클래스의 파라미터명을 사용하는 과정에서 전부 다 맞춰줘야 함. 즉 재사용성이 떨어짐
    public void stockRollBackOnOrder(Order order) {

        log.info("Redis 롤백 선언");

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        if(status == STATUS_ROLLED_BACK) {
                            log.info("Redis 롤백 시작");
                            stockOperation.increaseAll(order);
                            log.info("Redis 롤백 끝");
                        }
                    }
                }
        );

        log.info("Redis 롤백 선언 끝");
    }

    // 상품의 재고를 가져온다.
    @Transactional(readOnly = true)
    public int getStockRemaining(Product product) {
        return stockOperation.getStockRemaining(product);
    }
}
