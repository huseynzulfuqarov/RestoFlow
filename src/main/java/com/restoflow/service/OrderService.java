package com.restoflow.service;

import com.restoflow.domain.order.Order;
import com.restoflow.domain.order.OrderItem;
import com.restoflow.domain.product.CustomizationOption;
import com.restoflow.domain.product.Product;
import com.restoflow.dto.OrderItemRequestDTO;
import com.restoflow.enums.OrderStatus;
import com.restoflow.repository.OperationalSettingsRepository;
import com.restoflow.repository.OrderRepository;
import com.restoflow.repository.ProductRepository;
import com.restoflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private TimeEstimationService timeEstimationService;

    @Autowired
    private OperationalSettingsRepository settingsRepository;

    public List<Order> getCustomerOrderHistory(String username) {
        return userRepository.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .map(u -> {
                    if (u instanceof com.restoflow.domain.user.Customer) {
                        return ((com.restoflow.domain.user.Customer) u).getOrderHistory();
                    }
                    return new ArrayList<Order>();
                })
                .orElse(new ArrayList<>());
    }

    @Transactional
    public Order createOrderFromDTO(List<OrderItemRequestDTO> itemDTOs, String username) {
        checkKitchenCapacity();

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequestDTO dto : itemDTOs) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            List<CustomizationOption> selectedOptions = new ArrayList<>();
            if (dto.getSelectedOptionIds() != null) {
                for (Long optId : dto.getSelectedOptionIds()) {
                    product.getCustomizations().stream()
                            .flatMap(g -> g.getOptions().stream())
                            .filter(o -> o.getId().equals(optId))
                            .findFirst()
                            .ifPresent(selectedOptions::add);
                }
            }

            double itemPrice = pricingService.calculateDynamicPrice(product);
            for (CustomizationOption opt : selectedOptions) {
                itemPrice += opt.getSalesPrice();
            }

            items.add(new OrderItem(product, dto.getQuantity(), selectedOptions, itemPrice));
        }

        return createOrder(items, username);
    }

    // Overloaded method for backward compatibility (if needed) or internal use
    @Transactional
    public Order createOrder(List<OrderItem> items, String username) {
        double totalPrice = items.stream().mapToDouble(i -> i.getCalculatedPrice() * i.getQuantity()).sum();

        // Fix: Use Collectors.toList() for compatibility
        List<Product> products = items.stream().map(OrderItem::getProduct).collect(Collectors.toList());
        int estimatedTime = timeEstimationService.estimateDeliveryTimeInMinutes(products);

        Order order = new Order(items, OrderStatus.PENDING, LocalDateTime.now(), totalPrice,
                LocalDateTime.now().plusMinutes(estimatedTime));

        Order savedOrder = orderRepository.save(order);

        if (username != null) {
            userRepository.findAll().stream()
                    .filter(u -> u.getName().equals(username))
                    .findFirst()
                    .ifPresent(u -> {
                        if (u instanceof com.restoflow.domain.user.Customer) {
                            ((com.restoflow.domain.user.Customer) u).getOrderHistory().add(savedOrder);
                            userRepository.save(u);
                        }
                    });
        }

        return savedOrder;
    }

    // Method used by Simulation (RestoFlowApplication) which doesn't pass username
    @Transactional
    public Order createOrder(List<OrderItem> items) {
        return createOrder(items, null);
    }

    private void checkKitchenCapacity() {
        com.restoflow.domain.settings.OperationalSettings settings = settingsRepository.findById(1L)
                .orElse(new com.restoflow.domain.settings.OperationalSettings());

        long activeOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING || o.getStatus() == OrderStatus.PREPARING)
                .count();

        if (activeOrders >= settings.getMaxConcurrentOrders()) {
            throw new RuntimeException("Kitchen is currently overloaded. Please try again later.");
        }
    }
}
