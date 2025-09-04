package com.restoflow.repository;

import com.restoflow.domain.Ingredient;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryIngredientRepository implements Repository<Ingredient, Long> {
    private final Map<Long, Ingredient> storage = new HashMap<>();

    @Override
    public Ingredient save(Ingredient entity) {
        if (entity.getId() == null) {
            entity.setId(Ingredient.counter.incrementAndGet()); //bu hissede classin oz id generator'nu istifade ede bilmirem, bunun ucun public etdim.
        }
       return storage.put(entity.getId(), entity);
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Ingredient> findAll() {
        return  new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
