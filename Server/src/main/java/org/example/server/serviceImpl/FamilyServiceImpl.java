package org.example.server.serviceImpl;

import org.example.server.dto.FamilyDTO;
import org.example.server.entity.Family;
import org.example.server.repository.FamilyRepository;
import org.example.server.service.FamilyService;
import org.example.server.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final RedisService redisService;
    private final String familyKey = "family:";
    public FamilyServiceImpl(FamilyRepository familyRepository, RedisService redisService) {
        this.familyRepository = familyRepository;
        this.redisService = redisService;
    }

    @Transactional
    @Override
    public Family addFamily(Family family) {
        Family savedFamily = familyRepository.save(family);
        FamilyDTO familyDTO = FamilyDTO.builder()
                .id(savedFamily.getId())
                .familyName(savedFamily.getFamilyName())
                .familyCode(savedFamily.getFamilyCode())
                .createAt(savedFamily.getCreateAt())
                .build();
        redisService.saveRedis(familyKey + savedFamily.getFamilyCode(), familyDTO, Duration.ofHours(10));
        return savedFamily;
    }

    @Transactional
    @Override
    public void deleteFamily(String family) {
        redisService.deleteKey(familyKey + family);
        familyRepository.deleteFamilyByFamilyCode(family);
    }

    @Transactional
    @Override
    public FamilyDTO updateFamily(Family family) {
        Family savedFamily = familyRepository.save(family);
        FamilyDTO familyDTO = FamilyDTO.builder()
                .familyCode(family.getFamilyCode())
                .familyName(savedFamily.getFamilyName())
                .createAt(savedFamily.getCreateAt())
                .id(savedFamily.getId())
                .build();
        redisService.saveRedis(familyKey + savedFamily.getFamilyCode(), familyDTO, Duration.ofHours(10));
        return familyDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FamilyDTO> getFamily() {
        List<Family> families = familyRepository.findAll();
        return families.stream().map(r -> FamilyDTO.builder()
                .familyCode(r.getFamilyCode())
                .familyName(r.getFamilyName())
                .id(r.getId())
                .createAt(r.getCreateAt())
                .build()).collect(Collectors.toList());
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
                        .createAt(family.get().getCreateAt())
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
                    .createAt(family.get().getCreateAt())
                    .build();
            return familyDTO;
        }else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyDTO getFamilyByFamilyCode(String familyCode) {
        Optional<FamilyDTO> familyDTO = redisService.getRedis(familyKey + familyCode, FamilyDTO.class);
        if (familyDTO.isPresent()) {
            return familyDTO.get();
        }else{
            Optional<Family> family = familyRepository.findFamilyByFamilyCode(familyCode);
            if (family.isPresent()) {
                FamilyDTO familyDTO1 = FamilyDTO.builder()
                        .id(family.get().getId())
                        .familyName(family.get().getFamilyName())
                        .familyCode(family.get().getFamilyCode())
                        .createAt(family.get().getCreateAt())
                        .build();
                redisService.saveRedis(familyKey + familyCode, familyDTO1, Duration.ofHours(10));
                return familyDTO1;
            }else {
                return null;
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByFamilyCode(String familyCode) {
        return familyRepository.existsByFamilyCode(familyCode);
    }

    @Transactional(readOnly = true)
    @Override
    public Family getFamilyEntity(String familyCode) {
        return familyRepository.findFamilyByFamilyCode(familyCode).orElse(null);
    }
}
