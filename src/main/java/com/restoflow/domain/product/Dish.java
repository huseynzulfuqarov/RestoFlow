package com.restoflow.domain.product;

import com.restoflow.domain.inventory.Ingredient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Dish extends Product{
    private Map<Ingredient, Double> recipe;
    private int preparationTimeInMinutes;

    public Dish(String name, double price, int preparationTimeInMinutes) {
        super(name, price);
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        this.recipe = new HashMap<>();
    }
}
