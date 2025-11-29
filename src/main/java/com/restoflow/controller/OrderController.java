package com.restoflow.controller;

import com.restoflow.domain.order.Order;
import com.restoflow.dto.OrderItemRequestDTO;
import com.restoflow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody List<OrderItemRequestDTO> itemDTOs,
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        return ResponseEntity.ok(orderService.createOrderFromDTO(itemDTOs, username));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(@RequestHeader("X-User-Name") String username) {
        return ResponseEntity.ok(orderService.getCustomerOrderHistory(username));
    }
}
