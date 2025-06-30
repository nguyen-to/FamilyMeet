package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.FamilyControllerService;
import org.example.server.request.FamilyRequest;
import org.example.server.response.family.DashBoardResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyControllerService familyControllerService;
    @PostMapping
    public ResponseEntity<DataFormResponse<String>> createFamily(@RequestBody FamilyRequest familyRequest, Principal principal) {

        return ResponseEntity.ok(familyControllerService.SaveFamilyEntity(familyRequest,principal));
    }
    @GetMapping
    public ResponseEntity<DataFormResponse<DashBoardResponse>> getAllFamily(@RequestParam("familyId")Long familyId ,Principal principal) {
        return ResponseEntity.ok(familyControllerService.GetAllFamily(familyId,principal));
    }

}
