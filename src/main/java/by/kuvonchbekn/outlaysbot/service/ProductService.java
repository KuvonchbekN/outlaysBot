package by.kuvonchbekn.outlaysbot.service;


import by.kuvonchbekn.outlaysbot.dto.ProductDto;
import by.kuvonchbekn.outlaysbot.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    boolean deleteProduct(String productId);
    Product saveProduct(Product product);
    Product getProduct(String productId);
    List<Product> getAllProductsList();

    Product updateProduct(String productId, ProductDto productDto);
}

/*
* @GetMapping("/employees")
CollectionModel<EntityModel<Employee>> all() {

  List<EntityModel<Employee>> employees = repository.findAll().stream() //
      .map(assembler::toModel) //
      .collect(Collectors.toList());

  return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
*
*  */
