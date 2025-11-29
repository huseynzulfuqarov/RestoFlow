package com.restoflow.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class CustomizationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double costPrice;
    private double salesPrice;

    public CustomizationOption(String name, double costPrice, double salesPrice) {
        this.name = name;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
    }
}
