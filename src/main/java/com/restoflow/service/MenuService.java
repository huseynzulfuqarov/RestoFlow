package com.restoflow.service;

import com.restoflow.domain.DrinkItem;
import com.restoflow.domain.MenuItem;
import com.restoflow.repository.Repository;

import java.util.List;

public class MenuService {
    private final InventoryService inventoryService;
    private final Repository<MenuItem, Long> menuItemRepository;

    public MenuService(InventoryService inventoryService, Repository<MenuItem, Long> menuItemRepository) {
        this.inventoryService = inventoryService;
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem add(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return menuItem;
    }

    public List<MenuItem> getAvailableMenuItems(){
        menuItemRepository.findAll()
                .stream()
                .filter(allIng -> {
                    if(allIng instanceof DrinkItem){
                        if(allIng.get)
                    }
                } )
    }
}
