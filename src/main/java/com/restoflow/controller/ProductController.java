package com.restoflow.controller;

import com.restoflow.dto.ProductDetailDTO;
import com.restoflow.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/dishes")
    public ResponseEntity<List<ProductDetailDTO>> getDishes() {
        return ResponseEntity.ok(menuService.getAvailableDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetails(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getProductDetails(id));
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<ProductDetailDTO>> getRecommendations(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getRecommendedSideItems(id));
    }
}
