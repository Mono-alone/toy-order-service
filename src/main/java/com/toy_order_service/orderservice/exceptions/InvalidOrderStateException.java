package com.toy_order_service.orderservice.exceptions;

import com.toy_order_service.orderservice.enums.Status;

public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(String message) {
        super(message);
    }

    public InvalidOrderStateException(Status orderStatus, Status newStatus) {
        String message = String.format("Order status cannot be changed from %s to %s", orderStatus, newStatus);
        super(message);
    }

}
