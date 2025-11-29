package com.restoflow.service;

import com.restoflow.domain.order.Order;
import com.restoflow.domain.order.OrderItem;
import com.restoflow.domain.product.Dish;
import com.restoflow.domain.product.Product;
import com.restoflow.enums.OrderStatus;
import com.restoflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeEstimationService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public int estimateDeliveryTimeInMinutes(List<Product> newItems) {
        int currentKitchenLoad = 0;

        List<Order> activeOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING || o.getStatus() == OrderStatus.PREPARING)
                .collect(Collectors.toList());

        for (Order order : activeOrders) {
            for (OrderItem item : order.getItems()) {
                if (item.getProduct() instanceof Dish) {
                    currentKitchenLoad += ((Dish) item.getProduct()).getPreparationTime();
                }
            }
        }

        int newOrderTime = 0;
        for (Product product : newItems) {
            if (product instanceof Dish) {
                newOrderTime += ((Dish) product).getPreparationTime();
            }
        }

        int estimatedTime = (currentKitchenLoad / 2) + newOrderTime;

        return Math.max(estimatedTime, 15);
    }
}
