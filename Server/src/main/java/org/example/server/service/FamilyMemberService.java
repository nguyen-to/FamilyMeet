package org.example.server.service;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.entity.FamilyMember;
import org.example.server.request.FamilyRequest;

import java.util.List;

public interface FamilyMemberService {
    public FamilyMember addFamily(FamilyMember familyRequest);
    public List<FamilyMemberDTO> getFamilyId(Long id);
}
