package com.example.timedeal.order.service;

import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser) {

        /* 이벤트인 경우 / 일반 상품인 경우 => 이벤트인 경우 시간/재고 체크, 일반상품인 경우 재고 체크 ( 유효성 체크 )*/

        // product.validatedOnStock();

        /* 재고 감소 */

        /* 주문 진행 */

        /* 롤백 로직 => 이벤트 큐를 쓰면 더 효율적이나 시간상 이벤트 큐는 나중으로 미룬다. */
    }
}
