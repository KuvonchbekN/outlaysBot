package by.kuvonchbekn.outlaysbot.controller;

import by.kuvonchbekn.outlaysbot.dto.ApiResponse;
import by.kuvonchbekn.outlaysbot.dto.RoleToUserForm;
import by.kuvonchbekn.outlaysbot.entity.User;
import by.kuvonchbekn.outlaysbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserList(){
        return ResponseEntity.ok().body(userService.getUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        User userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?>getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping("/add_role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) throws RoleNotFoundException {
        ApiResponse apiResponse = userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().body(apiResponse);
    }

}


