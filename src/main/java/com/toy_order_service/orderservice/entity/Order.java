package com.toy_order_service.orderservice.entity;

import com.toy_order_service.orderservice.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    private Status status;

    private String customer;

    private String comment;

    private BigDecimal amount;

    @Column(updatable = false)
    private Instant createdAt;

    public Order(String customer, String comment, BigDecimal amount, Status status, Instant createdAt) {
        this.customer = customer;
        this.comment = comment;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }
}
