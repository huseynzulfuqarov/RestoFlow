package com.restoflow.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private int quantity;
    // Simplified for now, can add customization IDs later
}
