package com.restoflow.repository;

import com.restoflow.domain.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryIngredientRepository implements Repository<Ingredient, Long> {
    private final Map<Long, Ingredient> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Ingredient save(Ingredient entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter.incrementAndGet());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Ingredient> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
