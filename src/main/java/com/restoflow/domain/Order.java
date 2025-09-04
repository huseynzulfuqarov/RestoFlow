package com.restoflow.domain;

import com.restoflow.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private Double totalPrice;

    public Order(List<OrderItem> items, OrderStatus status, LocalDateTime orderDate, Double totalPrice) {
        this.items = items;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

}
