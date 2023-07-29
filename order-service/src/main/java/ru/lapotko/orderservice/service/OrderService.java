package ru.lapotko.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.lapotko.inventoryservice.dto.InventoryResponse;
import ru.lapotko.orderservice.exception.OrderApiException;
import ru.lapotko.orderservice.exception.OrderHasNoSkuCodesInStockException;
import ru.lapotko.orderservice.model.Order;
import ru.lapotko.orderservice.model.OrderLineItems;
import ru.lapotko.orderservice.repository.OrderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private static final String INVENTORY_SERVICE_URI = "http://inventory-service/api/inventory";
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public String placeOrder(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());

        InventoryResponse[] body = webClient.get()
                .uri(INVENTORY_SERVICE_URI, uriBuilder ->
                        uriBuilder.queryParam("skuCode",
                                        order.getOrderLineItemsList().stream()
                                                .map(OrderLineItems::getSkuCode)
                                                .collect(Collectors.toList()))
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (Objects.isNull(body))
            throw new OrderApiException("Inventory service webClient returns null error");

        List<InventoryResponse> response = Arrays.asList(body);

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
