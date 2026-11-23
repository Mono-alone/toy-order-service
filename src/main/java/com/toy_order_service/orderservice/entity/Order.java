package com.toy_order_service.orderservice.entity;

import com.toy_order_service.orderservice.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String customer;

    private String comment;

    private BigDecimal amount;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public Order(String customer, String comment, BigDecimal amount, Status status, Instant createdAt) {
        this.customer = customer;
        this.comment = comment;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }
}
