package com.restoflow.domain.inventory;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractStockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private String unit;
    private double costPerUnit;

    public AbstractStockItem() {
    }

    public AbstractStockItem(String name, double quantity, String unit, double costPerUnit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.costPerUnit = costPerUnit;
    }
}
