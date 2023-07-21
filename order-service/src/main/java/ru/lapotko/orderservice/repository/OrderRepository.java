package ru.lapotko.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lapotko.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
