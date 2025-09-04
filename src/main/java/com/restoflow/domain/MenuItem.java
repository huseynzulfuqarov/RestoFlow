package com.restoflow.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public abstract class MenuItem {
    private Long id;
    private String name;
    private Double price;
    public static final AtomicLong counter = new AtomicLong(0);

    public MenuItem(String name, Double price) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.price = price;
    }
}
