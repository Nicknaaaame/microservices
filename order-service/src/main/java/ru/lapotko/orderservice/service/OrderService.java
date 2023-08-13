package ru.lapotko.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lapotko.orderservice.dto.InventoryResponse;
import ru.lapotko.orderservice.exception.OrderHasNoSkuCodesInStockException;
import ru.lapotko.orderservice.model.Order;
import ru.lapotko.orderservice.model.OrderLineItems;
import ru.lapotko.orderservice.repository.OrderRepository;
import ru.lapotko.orderservice.service.feign.InventoryServiceClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryClient;

    public String placeOrder(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());
        List<InventoryResponse> response = inventoryClient.isInStock(order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList()));

        List<String> notInStockCodes = response.stream()
                .filter(inventory -> !inventory.isInStock()).map(InventoryResponse::getSkuCode)
                .toList();

        if (notInStockCodes.isEmpty()) {
            orderRepository.save(order);
            return "ok";
        }

        throw new OrderHasNoSkuCodesInStockException(order, notInStockCodes);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
