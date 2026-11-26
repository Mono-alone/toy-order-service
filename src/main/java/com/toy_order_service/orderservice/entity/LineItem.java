package com.toy_order_service.orderservice.entity;

import com.toy_order_service.orderservice.enums.LineItemType;
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
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "line_items")
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private LineItemType type;

    private BigDecimal amount;

    public LineItem(String name, LineItemType type, BigDecimal amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }
}
