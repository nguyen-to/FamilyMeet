package org.example.server.controllerservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.server.entity.Family;
import org.example.server.entity.FamilyMember;
import org.example.server.entity.UserEntity;
import org.example.server.response.formdata.DataFormResponse;
import org.example.server.service.FamilyMemberService;
import org.example.server.service.FamilyService;
import org.example.server.service.UserService;
import org.example.server.utill.FamilyRoles;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FMControllerService {
    private final FamilyService familyService;
    private final FamilyMemberService familyMemberService;
    private final UserService userService;

    public DataFormResponse<String> addFamilyMembers(String familyCode, Long familyId, Principal principal) {
        boolean existsByFamilyCode = familyService.existsByFamilyCode(familyCode);
        boolean existsMemberByFamilyId = familyMemberService.existsMemberByFamilyId(familyId);
        log.info("existsByFamilyCode: " + familyCode);
        log.info("existsByFamilyCode: " + existsByFamilyCode);
        log.info("existsMemberByFamilyCode: " + existsMemberByFamilyId);
        if (!existsByFamilyCode || existsMemberByFamilyId) {
            return DataFormResponse.<String>builder()
                    .message("Family does not exist ")
                    .build();
        }
        UserEntity userEntity  =  userService.findByEmail(principal.getName());
        Family family = familyService.getFamilyEntity(familyCode);
        FamilyMember familyMember = new FamilyMember();
        familyMember.setNickname(userEntity.getFullName());
        familyMember.setRoles(FamilyRoles.MEMBER);
        familyMember.setJoinAt(LocalDate.now());
        familyMember.setUser(userEntity);
        familyMember.setFamil(family);

        familyMemberService.addFamily(familyMember);

        return DataFormResponse.<String>builder()
                .message("Added family member")
                .build();
    }
    public DataFormResponse<String> deleteFamilyMembers(Long memberId, Principal principal) {
        String response = familyMemberService.deleteFamilyMember(memberId);
        return  DataFormResponse.<String>builder()
                .message(response)
                .build();
    }
}
