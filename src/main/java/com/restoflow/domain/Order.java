package com.restoflow.domain;

import com.restoflow.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Order {
    private Long id;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private Double totalPrice;
    public static final AtomicLong counter = new AtomicLong();

    public Order(List<OrderItem> items, OrderStatus status, LocalDateTime orderDate, Double totalPrice) {
        this.id = counter.incrementAndGet();
        this.items = items;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

}
