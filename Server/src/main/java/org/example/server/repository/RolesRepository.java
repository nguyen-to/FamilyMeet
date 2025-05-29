package org.example.server.repository;

import org.example.server.entity.Roles;
import org.example.server.utill.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
   Optional<Roles> findByRole(RolesEnum role);
}
