package com.restoflow.enums;

public enum Unit {
    KG("kilogram"),
    GR("gram"),
    METER("meter"),
    LITER("liter");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}