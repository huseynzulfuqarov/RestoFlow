package com.restoflow.repository;

import com.restoflow.domain.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryOrderRepository implements Repository<Order, Long> {
    private final Map<Long, Order> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Order> findById(Long aLong) {
        return Optional.ofNullable(storage.get(aLong));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long aLong) {
        storage.remove(aLong);
    }
}
