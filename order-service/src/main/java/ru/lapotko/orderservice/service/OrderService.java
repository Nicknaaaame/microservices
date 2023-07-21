package ru.lapotko.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lapotko.orderservice.repository.OrderRepository;
import ru.lapotko.orderservice.model.Order;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public String placeOrder(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());
        orderRepository.save(order);
        return "ok";
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
