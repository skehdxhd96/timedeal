package com.example.timedeal.order.service;

import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;

import java.util.List;

public interface OrderService {

    Order doOrder(OrderSaveRequest request, User currentUser);

    List<OrderSelectResponse> findMyOrderList(User currentUser);

    List<UserSelectResponse> findOrderedList(Long productId);

    OrderSelectResponse findOrderDetail(Long orderId, String orderStatus);
}
