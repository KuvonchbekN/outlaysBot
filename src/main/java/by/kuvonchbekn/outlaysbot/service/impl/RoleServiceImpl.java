package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.entity.Role;
import by.kuvonchbekn.outlaysbot.entity.RoleType;
import by.kuvonchbekn.outlaysbot.repo.RoleRepo;
import by.kuvonchbekn.outlaysbot.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Role saveOrFindRole(RoleType role){
        Optional<Role> byRoleType = roleRepo.findByRoleType(role);
        return byRoleType.orElseGet(() -> roleRepo.save(new Role(role)));
    }
}
