package com.restoflow.domain.product;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double manualPrice;
    private double overheadCost;
    private double profitMargin;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CustomizationGroup> customizations;

    private String imageUrl; // URL or Path to image

    public Product() {
    }

    public Product(String name, double manualPrice, double overheadCost, double profitMargin) {
        this.name = name;
        this.manualPrice = manualPrice;
        this.overheadCost = overheadCost;
        this.profitMargin = profitMargin;
    }
}
