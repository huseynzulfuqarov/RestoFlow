package com.restoflow.domain;

import lombok.Getter;

@Getter
public final class CustomizationOption {
    private final String name;
    private final Double price;

    public CustomizationOption(String name, Double price) {
        this.name = name;
        this.price = price;
    }

}
