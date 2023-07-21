package ru.lapotko.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lapotko.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
