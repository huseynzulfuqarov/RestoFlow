package com.restoflow.domain;

import com.restoflow.enums.Unit;
import lombok.Data;

@Data
public class Ingredient {
    private Long id;
    private String name;
    private Unit unit;
    private double quantityInStock;

    public Ingredient(String name, Unit unit, double quantityInStock) {
        this.name = name;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
    }
}
