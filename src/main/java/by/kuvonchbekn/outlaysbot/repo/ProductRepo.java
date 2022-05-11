package by.kuvonchbekn.outlaysbot.repo;

import by.kuvonchbekn.outlaysbot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
}
