package com.restoflow.service;

import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.domain.product.Dish;
import com.restoflow.domain.product.Product;
import com.restoflow.domain.settings.OperationalSettings;
import com.restoflow.repository.OperationalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PricingService {

    @Autowired
    private OperationalSettingsRepository settingsRepository;

    public double calculateDynamicPrice(Product product) {
        // Get current settings or default
        OperationalSettings settings = settingsRepository.findById(1L).orElse(new OperationalSettings());

        double baseCost = 0;

        if (product instanceof Dish) {
            Dish dish = (Dish) product;
            // 1. Ingredient Costs
            if (dish.getRecipe() != null) {
                for (Map.Entry<Ingredient, Double> entry : dish.getRecipe().entrySet()) {
                    baseCost += entry.getKey().getCostPerUnit() * entry.getValue();
                }
            }

            // 2. Labor & Utility Costs (Time based)
            double timeCost = dish.getPreparationTime()
                    * (settings.getLaborCostPerMinute() + settings.getUtilityCostPerMinute());
            baseCost += timeCost;
        }

        // 3. Overhead & Profit
        double totalCost = baseCost + product.getOverheadCost();
        return totalCost + product.getProfitMargin();
    }
}
