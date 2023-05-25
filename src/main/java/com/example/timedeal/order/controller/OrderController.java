package com.example.timedeal.order.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.service.OrderService;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @LoginCheck(role = LoginCheck.Role.GENERAL)
    @PostMapping
    public ResponseEntity<OrderSelectResponse> doOrder(@Valid @RequestBody OrderSaveRequest request, @CurrentUser User currentUser) {

        Order order = orderService.doOrder(request, currentUser);

        return ResponseEntity.ok(OrderSelectResponse.of(order));
    }

    @LoginCheck(role = LoginCheck.Role.GENERAL)
    @GetMapping("/myOrderList")
    public ResponseEntity<List<OrderSelectResponse>> myOrderList(@CurrentUser User currentUser) {

        List<OrderSelectResponse> myOrderList = orderService.findMyOrderList(currentUser);

        return ResponseEntity.ok(myOrderList);
    }

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @GetMapping("/orderedList")
    public ResponseEntity<List<UserSelectResponse>> orderedList(@RequestParam Long productId) {

        List<UserSelectResponse> orderedList = orderService.findOrderedList(productId);

        return ResponseEntity.ok(orderedList);
    }

    @LoginCheck(role = LoginCheck.Role.GENERAL)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderSelectResponse> showOrderDetail(
            @PathVariable Long orderId,
            @RequestParam(required = false) String orderStatus
    ) {
        OrderSelectResponse orderDetail = orderService.findOrderDetail(orderId, orderStatus);

        return ResponseEntity.ok(orderDetail);
    }
}
