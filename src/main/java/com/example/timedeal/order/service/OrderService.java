package com.example.timedeal.order.service;

import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.user.entity.User;

public interface OrderService {

    OrderSelectResponse doOrder(OrderSaveRequest request, User currentUser);
}
