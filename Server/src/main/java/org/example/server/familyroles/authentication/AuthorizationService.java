package org.example.server.familyroles.authentication;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.exception.FamilyException;
import org.example.server.repository.FamilyMemberRepository;
import org.example.server.repository.FamilyRepository;
import org.example.server.repository.GroupRepository;
import org.example.server.repository.UserRepository;
import org.example.server.service.FamilyMemberService;
import org.example.server.utill.FamilyRoles;
import org.example.server.utill.ResourceType;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final FamilyMemberService familyMemberService;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    public FamilyMemberDTO checkFamilyAccess(Long familyId, Long userId) {
        FamilyMemberDTO familyMember = familyMemberService.getByFamilyAndUserId(familyId,userId);
        if(familyMember != null){
            return familyMember;
        }else
            throw new FamilyException("Not a member of this family");
    }
    public boolean hasRole( Long familyId,Long userId, FamilyRoles[] requiredRoles) {
        FamilyMemberDTO member = checkFamilyAccess(userId, familyId);
        return Arrays.asList(requiredRoles).contains(Enum.valueOf(FamilyRoles.class,member.getRole()));
    }

    public boolean isResourceOwner(Long userId, Long resourceId, ResourceType resourceType) {
        switch (resourceType) {
            case CHANNEL:
                return groupRepository.findById(resourceId)
                        .map(channel -> channel.getOwner().getId().equals(userId))
                        .orElse(false);
            default:
                return false;
        }
    }
}
