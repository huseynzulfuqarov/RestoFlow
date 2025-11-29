package com.restoflow.domain.user;

import com.restoflow.enums.AdminRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    public Admin(String name, String password, AdminRole role) {
        super(name, password);
        this.role = role;
    }
}
