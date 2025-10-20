package org.example.server.controllerservice;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.FamilyDTO;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.dto.GroupDTO;
import org.example.server.entity.Family;
import org.example.server.entity.FamilyMember;
import org.example.server.entity.GroupEntity;
import org.example.server.entity.UserEntity;
import org.example.server.request.FamilyRequest;
import org.example.server.response.family.DashBoardResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.example.server.service.FamilyMemberService;
import org.example.server.service.FamilyService;
import org.example.server.service.GroupService;
import org.example.server.service.UserService;
import org.example.server.utill.FamilyRoles;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyControllerService {
    private final FamilyService familyService;
    private final FamilyMemberService familyMemberService;
    private final GroupService groupService;
    private final UserService userService;

    public  DataFormResponse<String> SaveFamilyEntity(FamilyRequest familyRequest,Principal principal) {

        FamilyDTO familyEntity = familyService.getFamilyByFamilyCode(familyRequest.getInviteCode());
        if (familyEntity != null) {
            return DataFormResponse.<String>builder()
                    .message("Family Exists")
                    .build();
        }
        String email = principal.getName();
        UserEntity userEntity = userService.findByEmail(email);
        try {
            Family newFamily = new Family();
            newFamily.setFamilyCode(familyRequest.getInviteCode());
            newFamily.setFamilyName(familyRequest.getName());
            newFamily.setCreateAt(LocalDate.now());

            Family savedFamily = familyService.addFamily(newFamily);

            FamilyMember newMember = new FamilyMember();
            newMember.setFamil(savedFamily);
            newMember.setUser(userEntity);
            newMember.setNickname(userEntity.getFullName());
            newMember.setJoinAt(LocalDate.now());
            newMember.setRoles(FamilyRoles.OWNER);

            familyMemberService.addFamily(newMember);

            GroupEntity newGroup = new GroupEntity();
            newGroup.setFamily(savedFamily);
            newGroup.setOwner(userEntity);
            newGroup.setName("general");
            newGroup.setDescription("Kênh Chat Tổng");
            newGroup.setCreateAt(LocalDateTime.now());
            groupService.addGroup(newGroup);
        }catch (Exception e) {
            e.printStackTrace();
        }


        return  DataFormResponse.<String>builder()
                .message("Family created")
                .build();
    }

    public DataFormResponse<DashBoardResponse> GetAllFamily(Long familyId ,Principal principal) {
        FamilyDTO familyDTO = familyService.getFamily(familyId);
        List<FamilyMemberDTO> familyMemberDTOS = familyMemberService.getFamilyId(familyId);
        List<GroupDTO> groupDTOS = groupService.groups(familyId);
        DashBoardResponse dashBoardResponse = DashBoardResponse.builder()
                .familyMembers(familyMemberDTOS)
                .groups(groupDTOS)
                .family(familyDTO)
                .build();
        return DataFormResponse.<DashBoardResponse>builder()
                .message("All Family")
                .data(dashBoardResponse)
                .build();
    }
}
