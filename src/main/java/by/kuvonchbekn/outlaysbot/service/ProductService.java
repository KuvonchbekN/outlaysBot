package by.kuvonchbekn.outlaysbot.service;


import by.kuvonchbekn.outlaysbot.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void deleteProduct(String productId);
    void saveProduct(Product product);
    Optional<Product> getProduct(String productId);
    List<Product> getAllProductsList();
}
