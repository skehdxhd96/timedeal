package com.example.timedeal.order.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.dto.OrderSelectResponse;
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

        OrderSelectResponse orderSelectResponse = orderService.doOrder(request, currentUser);

        return ResponseEntity.ok(orderSelectResponse);
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

    // TODO : 주문 상세 조회, 주문 리스트 조회 + 위 API에서 주문만 필요한가 전부 다 필요한가 ?
}
