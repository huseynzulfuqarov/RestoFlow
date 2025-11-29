package com.restoflow.domain.settings;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OperationalSettings {
    @Id
    private Long id = 1L; // Singleton

    private double laborCostPerMinute = 0.50; // Default $0.50/min
    private double utilityCostPerMinute = 0.20; // Default $0.20/min
    private int maxConcurrentOrders = 20; // Default 20

    // Default constructor
    public OperationalSettings() {
    }
}
