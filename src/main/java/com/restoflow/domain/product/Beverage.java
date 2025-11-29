package com.restoflow.domain.product;

import com.restoflow.domain.inventory.Consumable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Beverage extends Product {

    @OneToOne
    private Consumable sourceConsumable;

    public Beverage() {
        super();
    }

    public Beverage(String name, double manualPrice, double overheadCost, double profitMargin,
            Consumable sourceConsumable) {
        super(name, manualPrice, overheadCost, profitMargin);
        this.sourceConsumable = sourceConsumable;
    }
}
