package org.example.server.serviceImpl;

import org.example.server.entity.Roles;
import org.example.server.repository.RolesRepository;
import org.example.server.service.RolesService;
import org.example.server.utill.RolesEnum;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Roles getRoles(RolesEnum role) {
        return rolesRepository.findByRole(role).orElseGet(() -> {
            Roles roles = new Roles();
            roles.setRole(role);
            return rolesRepository.save(roles);
        });
    }
}
