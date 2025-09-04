package com.restoflow.service;

import com.restoflow.domain.Ingredient;
import com.restoflow.enums.Unit;
import com.restoflow.exception.NotFoundException;
import com.restoflow.repository.Repository;

import java.util.List;
import java.util.Map;

public class InventoryService {
    private Repository<Ingredient, Long> ingredientRepository;

    public InventoryService(Repository<Ingredient, Long> ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(String name, Unit unit, double quantity) {
        Ingredient ingredient = new Ingredient(name, unit, quantity);
        ingredientRepository.save(ingredient);
        return ingredient;
    }

    public Ingredient addToStock(Long ingredientId, double amount) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NotFoundException("Ingredient not found"));

        ingredient.setQuantityInStock(ingredient.getQuantityInStock() + amount);
        return ingredient;
    }

    public boolean checkStockAvailability(Map<Ingredient, Double> recipe) {
        return ingredientRepository.findAll().stream()
                .allMatch(allIng ->
                        allIng.getQuantityInStock() >= recipe.getOrDefault(allIng, 0D));
    }
    //burda mentiq bele olmalidir. demeli bizdeki resepde olan inqridenti gedib db de baxmalidir ve eger hamisi varsa o zaman true qaytarmalidir.

    public List<Ingredient> getLowStockIngredients(double threshold) {
        return  ingredientRepository.findAll()
                .stream()
                .filter(allIng -> allIng.getQuantityInStock() <= threshold)
                .toList();
    }
}
