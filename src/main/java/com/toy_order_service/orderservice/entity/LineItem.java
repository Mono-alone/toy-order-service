package com.toy_order_service.orderservice.entity;

import com.toy_order_service.orderservice.enums.LineItemType;
import jakarta.persistence.*;
import lombok.*;

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

    public LineItem(String name, LineItemType type) {
        this.name = name;
        this.type = type;
    }
}
