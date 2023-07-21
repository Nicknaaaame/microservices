package ru.lapotko.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lapotko.productservice.dto.ProductRequest;
import ru.lapotko.productservice.model.Product;
import ru.lapotko.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
