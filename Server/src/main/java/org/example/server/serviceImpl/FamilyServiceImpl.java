package org.example.server.serviceImpl;

import org.example.server.dto.FamilyDTO;
import org.example.server.entity.Family;
import org.example.server.repository.FamilyRepository;
import org.example.server.service.FamilyService;
import org.example.server.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final RedisService redisService;
    private final String familyKey = "family:";
    public FamilyServiceImpl(FamilyRepository familyRepository, RedisService redisService) {
        this.familyRepository = familyRepository;
        this.redisService = redisService;
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyDTO getFamily(Long id) {
        Optional<FamilyDTO> familyDTO = redisService.getRedis(familyKey + id, FamilyDTO.class);
        if (familyDTO.isPresent()) {
            return familyDTO.get();
        }else{
            Optional<Family> family = familyRepository.findById(id);
            if (family.isPresent()) {
                FamilyDTO familyDTO2 = FamilyDTO.builder()
                        .familyName(family.get().getFamilyName())
                        .familyCode(family.get().getFamilyCode())
                        .id(family.get().getId())
                        .build();
                redisService.saveRedis(familyKey + id, familyDTO2, Duration.ofHours(10));
                return familyDTO2;
            }else{
                return null;
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyDTO getFamilyByFamilyName(String familyName) {
        Optional<Family> family = familyRepository.findFamilyByFamilyName(familyName);
        if (family.isPresent()) {
            FamilyDTO familyDTO = FamilyDTO.builder()
                    .id(family.get().getId())
                    .familyName(family.get().getFamilyName())
                    .familyCode(family.get().getFamilyCode())
                    .build();
            return familyDTO;
        }else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyDTO getFamilyByFamilyCode(String familyCode) {
        Optional<FamilyDTO> familyDTO = redisService.getRedis(familyKey, FamilyDTO.class);
        if (familyDTO.isPresent()) {
            return familyDTO.get();
        }else{
            Optional<Family> family = familyRepository.findFamilyByFamilyCode(familyCode);
            if (family.isPresent()) {
                FamilyDTO familyDTO1 = FamilyDTO.builder()
                        .id(family.get().getId())
                        .familyName(family.get().getFamilyName())
                        .familyCode(family.get().getFamilyCode())
                        .build();
                redisService.saveRedis(familyKey + familyCode, familyDTO1, Duration.ofHours(10));
                return familyDTO1;
            }else {
                return null;
            }
        }
    }
}
