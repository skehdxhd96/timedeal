package com.example.timedeal.order.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.service.OrderService;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderSelectResponse> doOrder(@Valid @RequestBody OrderSaveRequest request, @CurrentUser User currentUser) {

        OrderSelectResponse orderSelectResponse = orderService.doOrder(request, currentUser);

        return ResponseEntity.ok(orderSelectResponse);
    }
}
