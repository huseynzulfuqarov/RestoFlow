package com.restoflow.domain.product;

import com.restoflow.domain.inventory.Consumable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Beverage extends Product {
    private Consumable sourceConsumable;
    public Beverage(String name, double price, Consumable sourceConsumable) {
        super(name, price);
        this.sourceConsumable = sourceConsumable;
    }
}
