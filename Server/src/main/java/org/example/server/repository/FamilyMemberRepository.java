package org.example.server.repository;

import org.example.server.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
   

    
}
