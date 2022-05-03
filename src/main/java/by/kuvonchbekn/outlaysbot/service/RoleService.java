package by.kuvonchbekn.outlaysbot.service;


import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.RoleType;

import java.util.Optional;

public interface RoleService {
    Role saveOrFindRole(RoleType roleType);
}
