package com.restoflow.controller;

import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.domain.order.Order;
import com.restoflow.domain.product.Dish;
import com.restoflow.domain.product.Product;
import com.restoflow.enums.OrderStatus;
import com.restoflow.repository.OrderRepository;
import com.restoflow.repository.ProductRepository;
import com.restoflow.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private com.restoflow.repository.OperationalSettingsRepository settingsRepository;

    @Autowired
    private com.restoflow.service.InventoryService inventoryService;

    // --- Order Management ---

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    // --- Recipe Management ---

    @PutMapping("/products/{id}/recipe")
    public ResponseEntity<Product> updateRecipe(@PathVariable Long id, @RequestBody Map<Long, Double> newRecipeIds) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product instanceof Dish) {
            Dish dish = (Dish) product;
            dish.getRecipe().clear();

            for (Map.Entry<Long, Double> entry : newRecipeIds.entrySet()) {
                Ingredient ingredient = (Ingredient) stockRepository.findById(entry.getKey())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found: " + entry.getKey()));
                dish.getRecipe().put(ingredient, entry.getValue());
            }
            return ResponseEntity.ok(productRepository.save(dish));
        } else {
            throw new RuntimeException("Product is not a Dish");
        }
    }

    // --- Operational Settings ---

    @GetMapping("/settings")
    public ResponseEntity<com.restoflow.domain.settings.OperationalSettings> getSettings() {
        return ResponseEntity
                .ok(settingsRepository.findById(1L).orElse(new com.restoflow.domain.settings.OperationalSettings()));
    }

    @PutMapping("/settings")
    public ResponseEntity<com.restoflow.domain.settings.OperationalSettings> updateSettings(
            @RequestBody com.restoflow.domain.settings.OperationalSettings newSettings) {
        newSettings.setId(1L); // Ensure singleton
        return ResponseEntity.ok(settingsRepository.save(newSettings));
    }

    // --- Smart Restock ---

    @GetMapping("/restock-predictions")
    public ResponseEntity<List<com.restoflow.domain.inventory.AbstractStockItem>> getRestockPredictions() {
        return ResponseEntity.ok(inventoryService.getSmartRestockPredictions());
    }
}
