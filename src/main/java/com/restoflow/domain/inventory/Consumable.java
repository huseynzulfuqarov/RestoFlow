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
public class Consumable extends AbstractStockItem {
    public Consumable() {
        super();
    }

    public Consumable(String name, double quantity, String unit, double costPerUnit) {
        super(name, quantity, unit, costPerUnit);
    }
}
