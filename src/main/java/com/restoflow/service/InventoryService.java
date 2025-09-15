package com.restoflow.service;

import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.enums.Unit;
import com.restoflow.repository.AbstractStockItemRepository;

public class InventoryService {
    private AbstractStockItemRepository abstractStockItemRepository;

    public InventoryService(AbstractStockItemRepository abstractStockItemRepository) {
        this.abstractStockItemRepository = abstractStockItemRepository;
    }


    public Ingredient addIngredient(String name, Unit unit, double quantity) {
        Ingredient ingredient = new Ingredient(name, unit, quantity);
        abstractStockItemRepository.save(ingredient);
        return ingredient;
    }
}
