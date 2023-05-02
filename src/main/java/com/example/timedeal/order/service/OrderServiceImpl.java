package com.example.timedeal.order.service;

import com.example.timedeal.order.dto.OrderItemSaveRequest;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.stock.service.StockHistoryService;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockHistoryService stockHistoryService;

    @Override
    @Transactional
    public OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser) {

        /* 이벤트인 경우 / 일반 상품인 경우 => 이벤트인 경우 시간/재고 체크, 일반상품인 경우 재고 체크 ( 유효성 체크 )*/
        List<Long> productIds = request.getOrderItemRequests().stream()
                .map(OrderItemSaveRequest::getProductId)
                .collect(Collectors.toList());

        List<Product> products = findProductByIds(productIds);
        // product.validatedOnStock();

        /* 재고 감소 */

        stockHistoryService.decrease(request.getProductId(), currentUser);

        /* 주문 진행 */

        /* 롤백 로직 => 이벤트 큐를 쓰면 더 효율적이나 시간상 이벤트 큐는 나중으로 미룬다. */
        return new OrderSelectResponse();
    }

    private List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductDetailByProductIds(productIds);
    }
}
