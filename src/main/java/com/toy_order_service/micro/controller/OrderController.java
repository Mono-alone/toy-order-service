package com.toy_order_service.micro.controller;

import com.toy_order_service.micro.entity.Order;
import com.toy_order_service.micro.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid Order newOrder) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(newOrder));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrderById(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") UUID orderId, @RequestBody @Valid Order orderToUpdate) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.updateOrder(orderId, orderToUpdate));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") UUID orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
