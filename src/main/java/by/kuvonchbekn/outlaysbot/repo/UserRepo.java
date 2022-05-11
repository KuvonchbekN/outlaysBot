package by.kuvonchbekn.outlaysbot.repo;

import by.kuvonchbekn.outlaysbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrPhoneNumberOrTelegramIdAndIsActive(String username,
                                                                      String phoneNumber,
                                                                      String telegramId,
                                                                      Boolean isActive);

    boolean existsByUsername(String username);
    Optional<User> findByTelegramId(String telegramId);
    boolean existsById(String id);
}
