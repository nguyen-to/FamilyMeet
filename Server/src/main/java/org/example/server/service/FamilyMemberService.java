package org.example.server.service;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.entity.FamilyMember;
import org.example.server.request.FamilyRequest;
import org.example.server.request.authrequest.MemberRolesRequest;

import java.util.List;

public interface FamilyMemberService {
    public FamilyMember addFamily(FamilyMember familyRequest);
    public List<FamilyMemberDTO> getFamilyId(Long id);
    public FamilyMemberDTO getByFamilyAndUserId(Long familyId, Long userId);
    public String deleteFamilyMember(Long id);
    public boolean existsMemberByFamilyId(Long familyCode);
    public String updateRolesFamilyMember(MemberRolesRequest memberRoles);
}
