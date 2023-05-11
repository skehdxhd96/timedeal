package com.example.timedeal.order.service;

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
import com.example.timedeal.stock.service.StockService;
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
    private final StockService stockService;

    @Override
    @Transactional
    public OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser) {

        // 빈 주문지 생성
        Order order = createEmptyOrder(currentUser);

        List<Product> products = findProductByIds(getProductIds(request)); // 여기서 flush 되는지 확인
        products.forEach(Product::validatedInEvent);

        /* 주문지에 주문아이템 삽입 */
        order.addOrderItems(OrderAssembler.orderItems(products, request, order));

        /* 재고 검사 */
        order.getOrderItems()
                .getElements()
                .forEach(o -> o.validatedOnStock(stockService.getStockRemaining(o.getProduct())));

        /* 재고 감소 */

//        stockService.decrease(request.getProductId(), currentUser);


        /* 주문 진행 */

        return new OrderSelectResponse();
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
