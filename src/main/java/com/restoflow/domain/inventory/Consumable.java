package com.restoflow.domain.inventory;

public class Consumable extends AbstractStockItem {
    private int quantityInStock;

    public Consumable(String name, int quantityInStock) {
        super(name);
        this.quantityInStock = quantityInStock;
    }
}
