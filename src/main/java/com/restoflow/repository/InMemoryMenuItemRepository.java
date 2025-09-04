package com.restoflow.repository;

import com.restoflow.domain.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMenuItemRepository implements Repository<MenuItem, Long> {
    private final Map<Long, MenuItem> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public MenuItem save(MenuItem entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<MenuItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
