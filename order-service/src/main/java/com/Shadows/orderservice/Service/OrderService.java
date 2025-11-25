package com.Shadows.orderservice.Service;

import com.Shadows.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrders();
    Optional<Order> getOrderById(String orderId);
    Order updateOrder(Order order);
    void deleteOrder(Long id);


}
