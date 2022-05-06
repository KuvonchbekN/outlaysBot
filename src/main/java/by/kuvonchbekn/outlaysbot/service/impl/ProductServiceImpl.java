package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.assemblers.ProductAssembler;
import by.kuvonchbekn.outlaysbot.dto.ProductDto;
import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.ProductNotFoundException;
import by.kuvonchbekn.outlaysbot.repo.ProductRepo;
import by.kuvonchbekn.outlaysbot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final MessageSource messageSource;
    @Override
    public void deleteProduct(String productId) {
        productRepo.deleteById(productId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product getProduct(String productId) {
        Optional<Product> byId = productRepo.findById(productId);
        if (byId.isEmpty()) throw new ProductNotFoundException(messageSource.getMessage("api.error.product.not.found", null, Locale.ENGLISH));
        return byId.get();
    }
    //need to implement assembler in productController

    @Override
    public List<Product> getAllProductsList() {
        return productRepo.findAll();
    }

    @Override
    public void updateProduct(String productId, ProductDto productDto) {

    }
}
