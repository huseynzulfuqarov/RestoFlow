package com.restoflow.service;

import com.restoflow.domain.inventory.AbstractStockItem;
import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.enums.Unit;
import com.restoflow.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private StockRepository stockRepository;

    public List<AbstractStockItem> getLowStockItems() {
        return stockRepository.findAll().stream()
                .filter(item -> item.getQuantity() < 10) // Threshold 10
                .collect(Collectors.toList());
    }

    public List<AbstractStockItem> getSmartRestockPredictions() {
        // Simple logic: If stock < 20, recommend buying up to 50.
        // In real app, this would use historical data.
        return stockRepository.findAll().stream()
                .filter(item -> item.getQuantity() < 20)
                .collect(Collectors.toList());
    }

    @Transactional
    public void restockItem(Long id, double quantity, double costPerUnit) {
        AbstractStockItem item = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(item.getQuantity() + quantity);
        item.setCostPerUnit(costPerUnit);
        stockRepository.save(item);
    }

    public Ingredient addIngredient(String name, Unit unit, double quantity, double costPerUnit) {
        Ingredient ingredient = new Ingredient(name, quantity, unit.name(), costPerUnit);
        stockRepository.save(ingredient);
        return ingredient;
    }
}
