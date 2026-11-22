package com.toy_order_service.orderservice.entity;

import com.toy_order_service.orderservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    @Size(max = 255)
    @Column(name = "customer")
    private String customer;

    @Column(name = "comment")
    @Size(max = 500)
    private String comment;

    @PositiveOrZero
    @Column(name = "amount", nullable = false)
    @NotNull
    private BigDecimal amount;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Instant createdAt;

    public Order(String customer, String comment, BigDecimal amount, Status status, Instant createdAt) {
        this.customer = customer;
        this.comment = comment;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }
}
