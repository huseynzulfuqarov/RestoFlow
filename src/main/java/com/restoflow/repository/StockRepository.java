package com.restoflow.repository;

import com.restoflow.domain.inventory.AbstractStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<AbstractStockItem, Long> {
}
