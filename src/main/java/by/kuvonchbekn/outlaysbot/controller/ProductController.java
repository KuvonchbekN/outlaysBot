package by.kuvonchbekn.outlaysbot.controller;

import by.kuvonchbekn.outlaysbot.assemblers.ProductAssembler;
import by.kuvonchbekn.outlaysbot.dto.ProductDto;
import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;


    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_OWNER')")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getSingleProductById(@PathVariable String productId){
        Product product = productService.getProduct(productId);
        EntityModel<Product> productEntityModel = productAssembler.toModel(product);
        return ResponseEntity.ok(productEntityModel);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_OWNER')")
    @GetMapping
    public ResponseEntity<?> getAllProductsList(){
        List<Product> allProductsList = productService.getAllProductsList();
        CollectionModel<EntityModel<Product>> collectionModel = productAssembler.toCustomCollectionModel(allProductsList);
        return ResponseEntity.ok(collectionModel);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        Product productSaved = productService.saveProduct(product);
        EntityModel<Product> productEntityModel = productAssembler.toModel(productSaved);
        return ResponseEntity.ok(productEntityModel);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId){
        boolean deleted = productService.deleteProduct(productId);
        if (deleted) ResponseEntity.ok();
        else ResponseEntity.badRequest();
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto){
        Product product = productService.updateProduct(productId, productDto);
        EntityModel<Product> productEntityModel = productAssembler.toModel(product);
        return ResponseEntity.ok(productEntityModel);
    }
}
