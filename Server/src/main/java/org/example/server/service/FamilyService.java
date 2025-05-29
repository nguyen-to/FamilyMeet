package org.example.server.service;

import org.example.server.dto.FamilyDTO;

public interface FamilyService {

    public FamilyDTO getFamily(Long id);
    public FamilyDTO getFamilyByFamilyName(String familyName);
    public FamilyDTO getFamilyByFamilyCode(String familyCode);
}
