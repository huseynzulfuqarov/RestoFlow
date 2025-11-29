package com.restoflow;

import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.domain.product.CustomizationGroup;
import com.restoflow.domain.product.CustomizationOption;
import com.restoflow.domain.product.Dish;
import com.restoflow.repository.ProductRepository;
import com.restoflow.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestoFlowApplication implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestoFlowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (stockRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {
        Ingredient flour = new Ingredient("Flour", 100, "kg", 2.0);
        Ingredient cheese = new Ingredient("Cheese", 50, "kg", 10.0);
        Ingredient tomato = new Ingredient("Tomato", 30, "kg", 3.0);
        stockRepository.saveAll(Arrays.asList(flour, cheese, tomato));

        Map<Ingredient, Double> pizzaRecipe = new HashMap<>();
        pizzaRecipe.put(flour, 0.2);
        pizzaRecipe.put(cheese, 0.1);
        pizzaRecipe.put(tomato, 0.1);

        Dish pizza = new Dish("Margherita Pizza", 0, 5.0, 1.5, pizzaRecipe, 20);
        pizza.setImageUrl(
                "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");

        CustomizationOption extraCheese = new CustomizationOption("Extra Cheese", 2.0, 3.5);
        CustomizationOption spicySauce = new CustomizationOption("Spicy Sauce", 0.5, 1.0);
        CustomizationGroup toppings = new CustomizationGroup("Toppings", Arrays.asList(extraCheese, spicySauce), false,
                null);
        pizza.setCustomizations(Collections.singletonList(toppings));

        productRepository.save(pizza);
    }
}
