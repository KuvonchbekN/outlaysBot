package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.assemblers.ProductAssembler;
import by.kuvonchbekn.outlaysbot.dto.ProductDto;
import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.ProductAlreadyExistsException;
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
    public boolean deleteProduct(String productId) {
        Optional<Product> byId = productRepo.findById(productId);
        try {
            if (byId.isEmpty()) throw new ProductNotFoundException(messageSource.getMessage("api.error.product.not.found", null, Locale.ENGLISH));
            productRepo.deleteById(productId);
            return true;
        }catch (ProductNotFoundException e){
            return false;
        }
    }

    @Override
    public Product saveProduct(Product product) {
        Optional<Product> byName = productRepo.findByName(product.getName());
        if (byName.isPresent()) throw new ProductAlreadyExistsException(messageSource.getMessage("api.error.product.already.exists", null, Locale.ENGLISH));
        return productRepo.save(product);
    }

    @Override
    public Product getProduct(String productId) {
        Optional<Product> byId = productRepo.findById(productId);
        if (byId.isEmpty()) throw new ProductNotFoundException(messageSource.getMessage("api.error.product.not.found", null, Locale.ENGLISH));
        return byId.get();
    }

    @Override
    public List<Product> getAllProductsList() {
        return productRepo.findAll();
    }

    @Override
    public Product updateProduct(String productId, ProductDto productDto) {
        Optional<Product> byId = productRepo.findById(productId);
        if (byId.isEmpty()) throw new ProductNotFoundException(messageSource.getMessage("api.error.product.not.found", null, Locale.ENGLISH));
        Product productSaved = byId.get();

        productSaved.setName(productDto.getName());
        productSaved.setPrice(productDto.getPrice());

        return productRepo.save(productSaved);
    }
}
