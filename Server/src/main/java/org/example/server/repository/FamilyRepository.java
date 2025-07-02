package org.example.server.repository;

import org.example.server.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family,Long> {
    Optional<Family> findFamilyByFamilyName(String familyName);
    Optional<Family> findFamilyByFamilyCode(String familyCode);
    void deleteFamilyByFamilyCode(String familyCode);
    boolean existsByFamilyCode(String familyCode);

}
