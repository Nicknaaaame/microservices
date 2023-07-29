package ru.lapotko.productservice.mapper;

import ru.lapotko.productservice.dto.ProductResponse;
import ru.lapotko.productservice.model.Product;

public class ProductResponseMapper {
    public ProductResponse map(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
