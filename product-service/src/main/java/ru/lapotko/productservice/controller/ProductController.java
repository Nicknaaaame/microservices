package ru.lapotko.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lapotko.productservice.dto.ProductRequest;
import ru.lapotko.productservice.dto.ProductResponse;
import ru.lapotko.productservice.mapper.ProductResponseMapper;
import ru.lapotko.productservice.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority(@Roles.PRODUCT_USER)")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @PreAuthorize("hasAuthority(@Roles.PRODUCT_USER)")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        var mapper = new ProductResponseMapper();
        return ResponseEntity.ok(productService.getAllProducts().stream()
                .map(mapper::map)
                .collect(Collectors.toList()));
    }

}
