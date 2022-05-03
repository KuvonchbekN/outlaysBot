package by.kuvonchbekn.outlaysbot.controller;

import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@RestController
public class ProductController {

    private final ProductService productService;


    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{productId}")
    public Optional<Product> getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping
    public List<Product> getAllProductsList(){
        return productService.getAllProductsList();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    @PostMapping
    public void addProduct(@RequestBody Product product){
        productService.saveProduct(product);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
    }
}
