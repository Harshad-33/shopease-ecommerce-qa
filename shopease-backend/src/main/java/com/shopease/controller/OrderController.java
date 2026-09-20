package com.shopease.controller;

import com.shopease.dto.OrderDto.CreateOrderRequest;
import com.shopease.dto.OrderDto.OrderResponse;
import com.shopease.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            Authentication authentication,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        OrderResponse response = orderService.placeOrder(authentication.getName(), request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(Authentication authentication) {
        List<OrderResponse> orders = orderService.getUserOrders(authentication.getName());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getUserOrderById(
            Authentication authentication,
            @PathVariable Long id
    ) {
        OrderResponse order = orderService.getUserOrderById(authentication.getName(), id);
        return ResponseEntity.ok(order);
    }
}
