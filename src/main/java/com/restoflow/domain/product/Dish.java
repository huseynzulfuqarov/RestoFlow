
package com.restoflow.domain.product;

import com.restoflow.domain.inventory.Ingredient;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Dish extends Product {

    @ElementCollection
    @CollectionTable(name = "dish_recipe", joinColumns = @JoinColumn(name = "dish_id"))
    @MapKeyJoinColumn(name = "ingredient_id")
    @Column(name = "quantity")
    private Map<Ingredient, Double> recipe; // Ingredient -> Quantity required

    private int preparationTime; // in minutes

    public Dish() {
        super();
    }

    public Dish(String name, double manualPrice, double overheadCost, double profitMargin, Map<Ingredient, Double> recipe, int preparationTime) {
        super(name, manualPrice, overheadCost, profitMargin);
        this.recipe = recipe;
        this.preparationTime = preparationTime;
    }
}
