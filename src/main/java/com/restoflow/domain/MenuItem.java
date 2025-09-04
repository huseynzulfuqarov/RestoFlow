package com.restoflow.domain;

import lombok.Data;

@Data
public abstract class MenuItem {
    private Long id;
    private String name;
    private Double price;

    public MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
