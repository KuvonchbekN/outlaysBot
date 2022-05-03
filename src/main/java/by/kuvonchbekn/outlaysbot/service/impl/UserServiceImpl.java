package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.dto.ApiResponse;
import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.User;
import by.kuvonchbekn.outlaysbot.repo.RoleRepo;
import by.kuvonchbekn.outlaysbot.repo.UserRepo;
import by.kuvonchbekn.outlaysbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepo.findByUsername(username);

        if (byUsername.isEmpty()) {
            log.info("User is not found in the database!");
            throw new UsernameNotFoundException("User not found in the database!");
        } else {
            log.info("User found in the database");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            User user = byUsername.get();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleType().name())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    authorities);
        }
    }

    @Override
    public List<User> getUserList() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> byUsername = userRepo.findByUsername(username);
        if (byUsername.isEmpty()) return null;

        return byUsername.get();
    }

    @Override
    public void saveUser(User user) {
        Optional<User> doesExist = userRepo.findByUsernameOrPhoneNumberOrTelegramIdAndIsActive(user.getUsername(),
                user.getPhoneNumber(),
                user.getTelegramId(),
                true);
        if (doesExist.isEmpty()) {
            userRepo.save(user);
            log.info("User {} is saved in the system", user);
        } else {
            log.info("User with {} username already exists in the system!", user.getUsername());
        }
    }

    @Override
    public ApiResponse addRoleToUser(String username, String roleName) {
        Optional<User> byUsername = userRepo.findByUsername(username);

        List<Role> roles = roleRepo.findAll();
        Role foundRole = roles.stream()
                .filter(role -> role.getRoleType().name().equals(roleName))
                .findAny()
                .orElse(null);


        if (foundRole == null) {
            return new ApiResponse("Role not found!", false);
        }

        if (byUsername.isEmpty()) {
            log.error("User with username {} is not found", username);
            return new ApiResponse(String.format("User with username %s is not found in the system", username), false);
        } else {
            User user = byUsername.get();
            user.getRoles().add(foundRole);
            User savedUser = userRepo.save(user);
            log.info("Role {} has been added to the username {}", roleName, username);
            return new ApiResponse("User", true, savedUser);
        }
    }


}
