package by.kuvonchbekn.outlaysbot.service;

import by.kuvonchbekn.outlaysbot.dto.ApiResponse;
import by.kuvonchbekn.outlaysbot.dto.LoginDto;
import by.kuvonchbekn.outlaysbot.dto.RegisterDto;
import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.RoleType;
import by.kuvonchbekn.outlaysbot.entity.User;
import by.kuvonchbekn.outlaysbot.filter.JwtHandler;
import by.kuvonchbekn.outlaysbot.repo.RoleRepo;
import by.kuvonchbekn.outlaysbot.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, RoleRepo roleRepo, @Lazy AuthenticationManager authenticationManager, JwtHandler jwtHandler, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse registerUser(RegisterDto registerDto) throws RoleNotFoundException {
        boolean existsByUsername = userRepo.existsByUsername(registerDto.getUsername());
        if (existsByUsername) return new ApiResponse("User with this username already exists!", false);


        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Optional<Role> byRoleType = roleRepo.findByRoleType(RoleType.ROLE_USER);
        if (byRoleType.isPresent()){
            user.setRoles(Set.of(byRoleType.get()));
        }else {
            throw new RoleNotFoundException("Role not found");
        }


        userRepo.save(user);
        return new ApiResponse("Saved successfully", true);
    }

    public ApiResponse login(LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            String token = jwtHandler.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token", true, token);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Password or username is incorrect", false);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepo.findByUsername(username);
        if (byUsername.isPresent())return byUsername.get();
        else throw new UsernameNotFoundException("User with this username is not found!");
    }
}
