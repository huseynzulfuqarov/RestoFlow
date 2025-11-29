package com.restoflow.domain.inventory;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Ingredient extends AbstractStockItem {
    public Ingredient() {
        super();
    }

    public Ingredient(String name, double quantity, String unit, double costPerUnit) {
        super(name, quantity, unit, costPerUnit);
    }
}
