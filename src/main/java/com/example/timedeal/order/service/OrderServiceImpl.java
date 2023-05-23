package com.example.timedeal.order.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.common.exception.StockException;
import com.example.timedeal.order.dto.OrderAssembler;
import com.example.timedeal.order.dto.OrderItemSaveRequest;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderStatus;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.stock.service.StockHistoryService;
import com.example.timedeal.stock.service.StockService;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.timedeal.stock.entity.StockHistoryType.MINUS;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockHistoryService stockHistoryService;
    private final StockService stockService;

    @Override
    @Transactional(noRollbackFor = {StockException.class})
    public OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser) {

        // 빈 주문지 생성
        Order order = createEmptyOrder(currentUser);

        List<Product> products = findProductByIds(getProductIds(request));
//        products.forEach(Product::validated);

        /* 주문지에 주문아이템 삽입 */
        order.addOrderItems(OrderAssembler.orderItems(products, request, order));

        /* 재고 감소 <락 걸고 진행해야 함.>*/
        stockService.decreaseStockOnOrder(order);

        stockHistoryService.saveHistory(currentUser, order, MINUS);

        /* 주문 진행 */
        return OrderSelectResponse.of(order);
    }

    // TODO : Indexing
    @Override
    @Transactional(readOnly = true)
    public List<OrderSelectResponse> findMyOrderList(User currentUser) {

        List<Order> orders = orderRepository.findAllByOrderedBy(currentUser);

        return orders.stream().map(OrderSelectResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<UserSelectResponse> findOrderedList(Long productId) {

        List<Order> orders = orderRepository.findOrderByProductId(productId);

        return orders.stream()
                .map(Order::getOrderedBy)
                .map(UserSelectResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public OrderSelectResponse findOrderDetail(Long orderId, String orderStatus) {

        Order order = orderRepository.findOrderByIdAndOrderStatus(orderId, OrderStatus.valueOf(orderStatus))
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        return OrderSelectResponse.of(order);
    }

    private List<Long> getProductIds(OrderSaveRequest request) {
        return request.getOrderItemRequests()
                                        .stream()
                                        .map(OrderItemSaveRequest::getProductId)
                                        .collect(Collectors.toList());
    }

    @Transactional
    public Order createEmptyOrder(User currentUser) {
        Order order = Order.builder()
                .orderedBy(currentUser)
                .orderStatus(OrderStatus.WAIT)
                .build();

        return orderRepository.saveAndFlush(order);
    }

    private List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductDetailByProductIds(productIds);
    }
}
