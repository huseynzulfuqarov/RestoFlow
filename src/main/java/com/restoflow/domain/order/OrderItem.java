package com.restoflow.domain.order;

import com.restoflow.domain.product.CustomizationOption;
import com.restoflow.domain.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    @ManyToMany
    private List<CustomizationOption> selectedOptions;

    private double calculatedPrice;

    public OrderItem(Product product, int quantity, List<CustomizationOption> selectedOptions, double calculatedPrice) {
        this.product = product;
        this.quantity = quantity;
        this.selectedOptions = selectedOptions;
        this.calculatedPrice = calculatedPrice;
    }
}
