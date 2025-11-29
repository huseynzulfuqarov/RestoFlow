package com.restoflow.service;

import com.restoflow.domain.product.Beverage;
import com.restoflow.domain.product.Dish;
import com.restoflow.domain.product.Product;
import com.restoflow.dto.CustomizationGroupDTO;
import com.restoflow.dto.CustomizationOptionDTO;
import com.restoflow.dto.ProductDetailDTO;
import com.restoflow.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDetailDTO> getAvailableDishes() {
        return productRepository.findAll().stream()
                .filter(p -> p instanceof Dish)
                .map(this::mapToDetailDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDetailDTO(product);
    }

    private ProductDetailDTO mapToDetailDTO(Product product) {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        // Dynamic price calculation
        // In a real app, we might cache this or calculate on read.
        // For now, we use the manual price or calculate it if we had a reference to
        // PricingService here.
        // But PricingService is logic. Let's assume we want to show the calculated
        // price.
        // We need to inject PricingService or move logic.
        // To avoid circular dependency (if any), let's keep it simple or inject lazily.
        // For this task, let's just use the manual price or a placeholder,
        // BUT the requirement says "Dynamic Pricing".
        // So we should probably use PricingService.
        // Let's refactor to allow PricingService usage or duplicate logic slightly for
        // display.
        // Actually, let's just set it to manualPrice for now and update it in
        // Controller using PricingService.
        dto.setBasePrice(product.getManualPrice());

        if (product instanceof Dish) {
            dto.setType("DISH");
            dto.setPreparationTime(((Dish) product).getPreparationTime());
        } else {
            dto.setType("BEVERAGE");
        }

        if (product.getCustomizations() != null) {
            dto.setCustomizationGroups(product.getCustomizations().stream().map(g -> {
                CustomizationGroupDTO gDto = new CustomizationGroupDTO();
                gDto.setId(g.getId());
                gDto.setName(g.getName());
                gDto.setRequired(g.isRequired());
                if (g.getDefaultOption() != null) {
                    gDto.setDefaultOptionId(g.getDefaultOption().getId());
                }
                gDto.setOptions(g.getOptions().stream().map(o -> {
                    CustomizationOptionDTO oDto = new CustomizationOptionDTO();
                    oDto.setId(o.getId());
                    oDto.setName(o.getName());
                    oDto.setPrice(o.getSalesPrice());
                    return oDto;
                }).collect(Collectors.toList()));
                return gDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProductDetailDTO> getRecommendedSideItems(Long productId) {
        Product selectedProduct = productRepository.findById(productId).orElse(null);
        if (selectedProduct == null)
            return new ArrayList<>();

        List<Product> recommendations = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();

        if (selectedProduct instanceof Dish) {
            // Recommend Beverages
            recommendations.addAll(allProducts.stream()
                    .filter(p -> p instanceof Beverage)
                    .limit(3)
                    .collect(Collectors.toList()));
        }

        return recommendations.stream().map(this::mapToDetailDTO).collect(Collectors.toList());
    }
}
