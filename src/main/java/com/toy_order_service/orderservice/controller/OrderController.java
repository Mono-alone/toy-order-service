package com.toy_order_service.orderservice.controller;

import com.toy_order_service.orderservice.entity.Order;
import com.toy_order_service.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") UUID orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<Void> pay(@PathVariable("id") UUID orderId) { // подумать над названием метода
        orderService.pay(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/ship/{id}")
    public ResponseEntity<Void> ship(@PathVariable("id") UUID orderId) { // подумать над названием метода
        orderService.ship(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") UUID orderId) { // подумать над названием метода
        orderService.cancel(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
