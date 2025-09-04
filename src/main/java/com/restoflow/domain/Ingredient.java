package com.restoflow.domain;

import com.restoflow.enums.Unit;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Ingredient {
    private Long id;
    private String name;
    private Unit unit;
    private double quantityInStock;
    public static final AtomicLong counter = new AtomicLong(0);

    public Ingredient(String name, Unit unit, double quantityInStock) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
    }
}
