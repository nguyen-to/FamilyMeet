package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.FMControllerService;
import org.example.server.familyroles.Annotatoin.AnnotationFamily;
import org.example.server.request.authrequest.MemberRolesRequest;
import org.example.server.response.formdata.DataFormResponse;
import org.example.server.utill.FamilyRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class FamilyMemberController {
    private final FMControllerService familyControllerService;

    @PostMapping("/{familyId}")
    @AnnotationFamily(requiredRoles = {FamilyRoles.OWNER, FamilyRoles.MODERATOR})
    public ResponseEntity<DataFormResponse<String>> addFamilyMember(
            @PathVariable("familyId") Long familyId,
            @RequestBody String familyCode,
            Principal principal
    ) {
        return ResponseEntity.ok(familyControllerService.addFamilyMembers(familyCode,familyId, principal));
    }

    @PatchMapping("/{familyId}")
    @AnnotationFamily(requiredRoles = {FamilyRoles.OWNER,FamilyRoles.MODERATOR})
    public ResponseEntity<DataFormResponse<String>> updateFamilyMember(@PathVariable("familyId") Long familyId, @RequestBody MemberRolesRequest memberRoles) {
        return ResponseEntity.ok(familyControllerService.updateRolesFamilyMember(memberRoles));
    }

    @DeleteMapping("/{familyId}")
    @AnnotationFamily(requiredRoles = {FamilyRoles.OWNER, FamilyRoles.MODERATOR})
    public ResponseEntity<DataFormResponse<String>> deleteFamilyMember(@RequestBody Long memberId,@PathVariable("familyId")Long familyId) {
        return ResponseEntity.ok(familyControllerService.deleteFamilyMembers(memberId));
    }
}
