package com.restoflow.repository;

import com.restoflow.domain.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements Repository<Order,Long> {
    private Map<Long, Order> storage = new HashMap<Long, Order>();

    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            entity.setId(Order.counter.incrementAndGet());
        }
        return storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<Order> findById(Long aLong) {
       return Optional.ofNullable(storage.get(aLong));
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {
        storage.remove(aLong);
    }
}
