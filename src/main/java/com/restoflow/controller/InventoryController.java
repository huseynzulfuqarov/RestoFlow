package com.restoflow.controller;

import com.restoflow.domain.inventory.AbstractStockItem;
import com.restoflow.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public ResponseEntity<List<AbstractStockItem>> getInventory() {
        return ResponseEntity.ok(stockRepository.findAll());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AbstractStockItem>> getLowStockAlerts() {
        return ResponseEntity.ok(stockRepository.findAll().stream()
                .filter(i -> i.getQuantity() < 10)
                .collect(Collectors.toList()));
    }

    @PostMapping("/restock")
    public ResponseEntity<AbstractStockItem> restock(@RequestParam Long id, @RequestParam double quantity,
            @RequestParam double costPerUnit) {
        AbstractStockItem item = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(item.getQuantity() + quantity);
        item.setCostPerUnit(costPerUnit);

        return ResponseEntity.ok(stockRepository.save(item));
    }
}
