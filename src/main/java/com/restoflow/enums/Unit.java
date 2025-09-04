package com.restoflow.enums;

import lombok.Getter;

@Getter
public enum Unit {
    KG("kilogram"),
    GR("gram"),
    METER("meter"),
    LITER("liter");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }
}