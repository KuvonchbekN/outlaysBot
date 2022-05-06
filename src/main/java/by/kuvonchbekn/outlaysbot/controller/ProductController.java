package by.kuvonchbekn.outlaysbot.controller;

import by.kuvonchbekn.outlaysbot.assemblers.ProductAssembler;
import by.kuvonchbekn.outlaysbot.dto.ProductDto;
import by.kuvonchbekn.outlaysbot.entity.Product;
import by.kuvonchbekn.outlaysbot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;


    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getSingleProductById(@PathVariable String productId){
        Product product = productService.getProduct(productId);
        EntityModel<Product> productEntityModel = productAssembler.toModel(product);
        return ResponseEntity.ok(productEntityModel);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getAllProductsList(){
        List<Product> allProductsList = productService.getAllProductsList();
        CollectionModel<EntityModel<Product>> collectionModel = productAssembler.toCustomCollectionModel(allProductsList);
        return ResponseEntity.ok(collectionModel);
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

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto){
        productService.updateProduct(productId, productDto);
        return null;
    }
}
