package com.restoflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderItemRequestDTO {
    private Long productId;
    private int quantity;
    private List<Long> selectedOptionIds; // IDs of selected CustomizationOptions
}
