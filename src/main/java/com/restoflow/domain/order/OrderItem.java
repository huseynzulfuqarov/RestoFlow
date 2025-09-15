package com.restoflow.domain.order;

import com.restoflow.domain.old.MenuItem;
import lombok.Data;

@Data
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
}
