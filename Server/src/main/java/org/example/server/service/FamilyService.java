package org.example.server.service;

import org.example.server.dto.FamilyDTO;
import org.example.server.entity.Family;

import java.util.List;

public interface FamilyService {
    public FamilyDTO addFamily(Family family);
    void deleteFamily(String family);
    public FamilyDTO updateFamily(Family family);

    public List<FamilyDTO> getFamily();
    public FamilyDTO getFamily(Long id);
    public FamilyDTO getFamilyByFamilyName(String familyName);
    public FamilyDTO getFamilyByFamilyCode(String familyCode);
}
