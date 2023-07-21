package ru.lapotko.orderservice.mapper;

import ru.lapotko.orderservice.dto.OrderLineItemsDto;
import ru.lapotko.orderservice.model.OrderLineItems;

public class OrderLineItemsMapper {
    public OrderLineItemsDto map(OrderLineItems entity) {
        return OrderLineItemsDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .skuCode(entity.getSkuCode())
                .build();
    }

    public OrderLineItems map(OrderLineItemsDto dto) {
        OrderLineItems model = new OrderLineItems();
        model.setPrice(dto.getPrice());
        model.setQuantity(dto.getQuantity());
        model.setSkuCode(dto.getSkuCode());
        return model;
    }
}
