package com.restoflow.domain.user;

import com.restoflow.domain.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends User {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderHistory;

    public Customer(String name, String password) {
        super(name, password);
    }
}
