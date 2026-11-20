package com.toy_order_service.micro.service;

import com.toy_order_service.micro.entity.Order;
import com.toy_order_service.micro.enums.Status;
import com.toy_order_service.micro.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order newOrder) {
        Order order = new Order(
                newOrder.getCustomer(),
                newOrder.getComment(),
                newOrder.getAmount(),
                Status.NEW,
                LocalDateTime.now()
        );

        return orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: id = " + orderId));
    }

    public Order updateOrder(UUID orderId, Order orderToUpdate) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: id = " + orderId));

        order.setStatus(orderToUpdate.getStatus());
        order.setAmount(orderToUpdate.getAmount());
        order.setComment(orderToUpdate.getComment());
        order.setCustomer(orderToUpdate.getCustomer());

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(UUID orderId) {
        Order deleteOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: id = " + orderId));
        orderRepository.delete(deleteOrder);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
