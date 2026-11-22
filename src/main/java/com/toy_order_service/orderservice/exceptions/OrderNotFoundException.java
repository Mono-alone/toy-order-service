package com.toy_order_service.orderservice.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(UUID id) {
        super("Order not found: id = " + id);
    }
}