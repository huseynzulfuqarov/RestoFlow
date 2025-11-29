package com.restoflow.domain.order;

import com.restoflow.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurant_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;
    private Double totalPrice;
    private LocalDateTime estimatedReadyTime;

    public Order(List<OrderItem> items, OrderStatus status, LocalDateTime orderDate, Double totalPrice,
            LocalDateTime estimatedReadyTime) {
        this.items = items;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.estimatedReadyTime = estimatedReadyTime;
    }
}
