package com.restoflow;

import com.restoflow.domain.inventory.Ingredient;
import com.restoflow.domain.order.Order;
import com.restoflow.domain.order.OrderItem;
import com.restoflow.domain.product.CustomizationGroup;
import com.restoflow.domain.product.CustomizationOption;
import com.restoflow.domain.product.Dish;
import com.restoflow.domain.product.Product;
import com.restoflow.dto.ProductDetailDTO;
import com.restoflow.repository.ProductRepository;
import com.restoflow.repository.StockRepository;
import com.restoflow.service.MenuService;
import com.restoflow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class RestoFlowApplication implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(RestoFlowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("   RESTOFLOW SIMULATION STARTED");
        System.out.println("==========================================");

        // 1. Admin: Add Stock
        System.out.println("\n[ADMIN] Adding Stock...");
        Ingredient flour = new Ingredient("Flour", 100, "kg", 2.0);
        Ingredient cheese = new Ingredient("Cheese", 50, "kg", 10.0);
        Ingredient tomato = new Ingredient("Tomato", 30, "kg", 3.0);
        stockRepository.saveAll(Arrays.asList(flour, cheese, tomato));

        // 2. Admin: Create Menu
        System.out.println("[ADMIN] Creating Menu...");
        Map<Ingredient, Double> pizzaRecipe = new HashMap<>();
        pizzaRecipe.put(flour, 0.2);
        pizzaRecipe.put(cheese, 0.1);
        pizzaRecipe.put(tomato, 0.1);

        Dish pizza = new Dish("Margherita Pizza", 0, 5.0, 1.5, pizzaRecipe, 20);

        // Add Customization
        CustomizationOption extraCheese = new CustomizationOption("Extra Cheese", 2.0, 3.5);
        CustomizationOption spicySauce = new CustomizationOption("Spicy Sauce", 0.5, 1.0);
        CustomizationGroup toppings = new CustomizationGroup("Toppings", Arrays.asList(extraCheese, spicySauce), false,
                null);
        pizza.setCustomizations(Collections.singletonList(toppings));

        productRepository.save(pizza);

        // 3. Customer: View Menu
        System.out.println("\n[CUSTOMER] Viewing Menu...");
        List<ProductDetailDTO> menu = menuService.getAvailableDishes();
        menu.forEach(p -> System.out.println(" - " + p.getName()));

        // 4. Customer: Select Pizza & Customize
        System.out.println("\n[CUSTOMER] Selecting Pizza...");
        System.out.println("[SYSTEM] Customization Options for " + pizza.getName() + ":");
        pizza.getCustomizations().forEach(g -> {
            System.out.println("  Group: " + g.getName());
            g.getOptions().forEach(o -> System.out.println("    - " + o.getName() + " (+$" + o.getSalesPrice() + ")"));
        });

        System.out.println("[CUSTOMER] I want Extra Cheese.");
        List<CustomizationOption> selectedOptions = Collections.singletonList(extraCheese);

        // 5. System: Recommendation
        System.out.println("\n[SYSTEM] Recommendation: People who ordered Pizza also ordered Cola.");
        List<ProductDetailDTO> recommendations = menuService.getRecommendedSideItems(pizza.getId());
        if (!recommendations.isEmpty()) {
            System.out.println("Recommended: " + recommendations.get(0).getName());
        }

        // 6. Customer: Place Order
        System.out.println("\n[CUSTOMER] Placing Order...");
        OrderItem item = new OrderItem(pizza, 1, selectedOptions, 0); // Price calculated by service
        Order order = orderService.createOrder(Collections.singletonList(item));

        // 7. System: Estimate Time
        System.out.println("[SYSTEM] Order Placed! Total Price: $" + order.getTotalPrice());
        System.out.println("[SYSTEM] Estimated Ready Time: " + order.getEstimatedReadyTime());
        System.out.println("==========================================");
    }
}
