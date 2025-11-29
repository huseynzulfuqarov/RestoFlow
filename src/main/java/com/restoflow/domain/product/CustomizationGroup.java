package com.restoflow.domain.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class CustomizationGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CustomizationOption> options;

    private boolean isRequired;

    @OneToOne
    private CustomizationOption defaultOption;

    public CustomizationGroup(String name, List<CustomizationOption> options, boolean isRequired,
            CustomizationOption defaultOption) {
        this.name = name;
        this.options = options;
        this.isRequired = isRequired;
        this.defaultOption = defaultOption;
    }
}
