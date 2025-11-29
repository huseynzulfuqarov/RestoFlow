package com.restoflow.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String type; // Dish or Beverage
    private int preparationTime; // For Dish
}
