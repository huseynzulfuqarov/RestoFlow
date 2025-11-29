package com.restoflow.dto;

import lombok.Data;
import java.util.List;

@Data
public class CustomizationGroupDTO {
    private Long id;
    private String name;
    private boolean isRequired;
    private Long defaultOptionId;
    private List<CustomizationOptionDTO> options;
}
