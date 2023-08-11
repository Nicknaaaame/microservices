package ru.lapotko.productservice.setup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.lapotko.productservice.model.Product;
import ru.lapotko.productservice.repository.ProductRepository;

import java.math.BigDecimal;

@Component
@Profile({"dev"})
@RequiredArgsConstructor
public class LocalDataSetup implements ApplicationRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productRepository.save(Product.builder()
                .name("IPhone 4s")
                .description("IPhone 4s white")
                .price(new BigDecimal(100))
                .build());
        productRepository.save(Product.builder()
                .name("IPhone 5s")
                .description("IPhone 5s black")
                .price(new BigDecimal(200))
                .build());
        productRepository.save(Product.builder()
                .name("IPhone 6s")
                .description("IPhone 6s pink gold")
                .price(new BigDecimal(300))
                .build());
    }
}
