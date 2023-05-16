package com.example.timedeal.order.service;

import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;

import java.util.List;

public interface OrderService {

    OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser);

    List<OrderSelectResponse> findMyOrderList(User currentUser);

    List<UserSelectResponse> findOrderedList(Long productId);
}
