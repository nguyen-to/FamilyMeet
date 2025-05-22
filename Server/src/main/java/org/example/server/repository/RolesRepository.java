package org.example.server.repository;

import org.example.server.entity.Roles;
import org.example.server.utill.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles,Long> {
   Optional<Roles> findByRole(RolesEnum role);
}
