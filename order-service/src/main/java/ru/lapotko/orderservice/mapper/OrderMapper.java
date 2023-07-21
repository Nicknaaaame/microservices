package ru.lapotko.orderservice.mapper;

import ru.lapotko.orderservice.dto.OrderRequest;
import ru.lapotko.orderservice.dto.OrderResponse;
import ru.lapotko.orderservice.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    private final OrderLineItemsMapper orderLineItemsMapper = new OrderLineItemsMapper();

    public Order map(OrderRequest request) {
        Order order = new Order();
        order.setOrderLineItemsList(request.getOrderLineItems().stream()
                .map(orderLineItemsMapper::map)
                .collect(Collectors.toList())
        );
        return order;
    }

    public OrderResponse map(Order entity) {
        return OrderResponse.builder()
                .orderLineItems(entity.getOrderLineItemsList().stream()
                        .map(orderLineItemsMapper::map)
                        .collect(Collectors.toList()))
                .build();
    }
}
