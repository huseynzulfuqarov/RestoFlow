package com.restoflow.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String role; // "CUSTOMER", "ADMIN", "INVENTORY_ADMIN"
}
