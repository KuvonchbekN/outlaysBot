package by.kuvonchbekn.outlaysbot.service;

import by.kuvonchbekn.outlaysbot.dto.ApiResponse;
import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {
    List<User> getUserList();
    User getUserByUsername(String username);
    void saveUser(User user);
    ApiResponse addRoleToUser(String username, String roleName) throws RoleNotFoundException;
}
