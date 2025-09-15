package com.restoflow.domain.inventory;

import com.restoflow.enums.Unit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Ingredient extends AbstractStockItem{
    private Unit unit;
    private double quantityInStock;

    public Ingredient(String name, Unit unit, double quantityInStock) {
        super(name);
        this.unit = unit;
        this.quantityInStock = quantityInStock;
    }
}
