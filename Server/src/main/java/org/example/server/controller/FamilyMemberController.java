package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.FMControllerService;
import org.example.server.familyroles.Annotatoin.AnnotationFamily;
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
    @AnnotationFamily(requiredRoles = FamilyRoles.MEMBER)
    public ResponseEntity<DataFormResponse<String>> addFamilyMember(
            @PathVariable Long familyId,
            @RequestBody String familyCode,
            Principal principal
    ) {
        return ResponseEntity.ok(familyControllerService.addFamilyMembers(familyCode,familyId, principal));
    }


    @DeleteMapping
    public ResponseEntity<DataFormResponse<String>> deleteFamilyMember(@RequestBody Long memberId, Principal principal) {
        return ResponseEntity.ok(familyControllerService.deleteFamilyMembers(memberId,principal));
    }
}
