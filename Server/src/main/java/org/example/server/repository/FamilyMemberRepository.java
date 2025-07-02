package org.example.server.repository;

import org.example.server.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findFamilyMemberByFamil_Id(Long id);
    Optional<FamilyMember> findFamilyMemberByFamil_IdAndUser_Id(Long id, Long userId);
    void deleteById(Long id);

    boolean existsByFamil_Id(Long familId);
}
