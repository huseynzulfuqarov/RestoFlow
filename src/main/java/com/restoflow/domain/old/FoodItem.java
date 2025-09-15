package com.restoflow.domain.old;


import com.restoflow.domain.inventory.Ingredient;

import java.util.Map;

public class FoodItem extends MenuItem {
    private int basePreparationTimeInMinutes;
    private Map<Ingredient, Double> recipe;

    public FoodItem(String name, Double price, int basePreparationTimeInMinutes, Map<Ingredient, Double> recipe) {
        super(name, price);
        this.basePreparationTimeInMinutes = basePreparationTimeInMinutes;
        this.recipe = recipe;
    }
}
