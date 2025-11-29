package com.restoflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductDetailDTO {
    private Long id;
    private String name;
    private double basePrice;
    private String type; // DISH, BEVERAGE
    private int preparationTime;
    private List<CustomizationGroupDTO> customizationGroups;
}
