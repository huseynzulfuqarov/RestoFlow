package com.restoflow.repository;

import com.restoflow.domain.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryMenuItemRepository implements Repository<MenuItem, Long> {
    private Map<Long, MenuItem> storage = new HashMap<Long, MenuItem>();
    @Override
    public MenuItem save(MenuItem entity) {
        if (entity.getId() == null) {
            entity.setId(MenuItem.counter.incrementAndGet());
        }
        return storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<MenuItem> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
