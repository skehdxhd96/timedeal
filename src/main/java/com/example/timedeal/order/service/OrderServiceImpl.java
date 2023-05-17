package com.example.timedeal.order.service;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.order.dto.OrderAssembler;
import com.example.timedeal.order.dto.OrderItemSaveRequest;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.order.entity.OrderItems;
import com.example.timedeal.order.entity.OrderStatus;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.stock.dto.StockAssembler;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.entity.StockHistoryType;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.stock.service.StockService;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.timedeal.stock.entity.StockHistoryType.MINUS;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockService stockService;

    @Override
    @Transactional
    public OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser) {

        // 빈 주문지 생성
        Order order = createEmptyOrder(currentUser);
        orderRepository.saveAndFlush(order);

        List<Product> products = findProductByIds(getProductIds(request));
        products.forEach(Product::validatedInEvent);

        /* 주문지에 주문아이템 삽입 */
        order.addOrderItems(OrderAssembler.orderItems(products, request, order));

        /* 재고 검사 */
        order.getOrderItems()
                .getElements()
                .forEach(o -> o.validatedOnStock(stockService.getStockRemaining(o.getProduct())));

        /* 재고 감소 <락 걸고 진행해야 함.>*/
        stockService.decreaseStockOnOrder(order);

        saveHistory(currentUser, order);

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

        Order order = orderRepository.findOrderByIdAndAndOrderStatus(orderId, orderStatus)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        return OrderSelectResponse.of(order);
    }

    @Transactional
    public void saveHistory(User currentUser, Order order) {
        List<StockHistory> StockHistory = order.getOrderItems().getElements()
                .stream()
                .map(o -> StockAssembler.stockHistory(o, currentUser, MINUS))
                .collect(Collectors.toList());

        stockHistoryRepository.saveAll(StockHistory);
    }

    private List<Long> getProductIds(OrderSaveRequest request) {
        return request.getOrderItemRequests()
                                        .stream()
                                        .map(OrderItemSaveRequest::getProductId)
                                        .collect(Collectors.toList());
    }

    public Order createEmptyOrder(User currentUser) {
        Order order = Order.builder()
                .orderedBy(currentUser)
                .orderStatus(OrderStatus.WAIT)
                .build();

        return orderRepository.save(order);
    }

    private List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductDetailByProductIds(productIds);
    }
}
