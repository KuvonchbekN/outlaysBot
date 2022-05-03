package by.kuvonchbekn.outlaysbot.controller;

import by.kuvonchbekn.outlaysbot.dto.ApiResponse;
import by.kuvonchbekn.outlaysbot.dto.LoginDto;
import by.kuvonchbekn.outlaysbot.dto.RegisterDto;
import by.kuvonchbekn.outlaysbot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) throws RoleNotFoundException {
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse.getMessage());
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse login = authService.login(loginDto);
        return ResponseEntity.status(login.isSuccess() ? 201 : 409).body(login);
    }
}
