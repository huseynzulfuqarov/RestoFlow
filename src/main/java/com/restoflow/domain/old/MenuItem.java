package com.restoflow.domain.old;

import lombok.Data;

@Data
public abstract class MenuItem {
    private Long id;
    private String name;
    private Double price;

    protected MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
