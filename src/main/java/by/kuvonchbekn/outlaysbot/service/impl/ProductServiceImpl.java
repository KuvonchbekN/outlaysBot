package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.repo.ProductRepo;
import by.kuvonchbekn.outlaysbot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    @Override
    public void deleteProduct(String productId) {
        productRepo.deleteById(productId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Optional<Product> getProduct(String productId) {
        return productRepo.findById(productId);
    }

    @Override
    public List<Product> getAllProductsList() {
        return productRepo.findAll();
    }
}
