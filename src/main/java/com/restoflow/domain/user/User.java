package com.restoflow.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "app_user") // User is a reserved keyword in SQL
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password; // Basic hashing will be done manually

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
