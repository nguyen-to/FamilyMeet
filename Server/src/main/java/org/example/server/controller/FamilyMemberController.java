package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.FMControllerService;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class FamilyMemberController {
    private final FMControllerService familyControllerService;
    @PostMapping
    public ResponseEntity<DataFormResponse<String>> addFamilyMember(@RequestBody String familyCode, Principal principal) {

        return ResponseEntity.ok(familyControllerService.addFamilyMembers(familyCode,principal));
    }
}
