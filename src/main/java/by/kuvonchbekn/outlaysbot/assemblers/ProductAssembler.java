package by.kuvonchbekn.outlaysbot.assemblers;

import by.kuvonchbekn.outlaysbot.controller.ProductController;
import by.kuvonchbekn.outlaysbot.entity.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {
        return EntityModel.of(product, linkTo(methodOn(ProductController.class).getSingleProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProductsList()).withRel("products"));
    }

    @Override
    public CollectionModel<EntityModel<Product>> toCollectionModel(Iterable<? extends Product> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    //custom method by me, I don't know if this works or not
    public CollectionModel<EntityModel<Product>> toCustomCollectionModel(List<Product> allProductsList){
        List<EntityModel<Product>> products = new ArrayList<>();
        for (Product product : allProductsList) {
            EntityModel<Product> productEntityModel = toModel(product);
            products.add(productEntityModel);
        }
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getAllProductsList()).withSelfRel());
        return collectionModel;
    }
}
