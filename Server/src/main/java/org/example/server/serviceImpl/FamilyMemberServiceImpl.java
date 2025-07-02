package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.entity.FamilyMember;
import org.example.server.repository.FamilyMemberRepository;
import org.example.server.request.FamilyRequest;
import org.example.server.service.FamilyMemberService;
import org.example.server.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyMemberServiceImpl implements FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;
    private final RedisService redisService;
    private String familyMemberKey = "FamilyMember:";
    @Transactional
    @Override
    public FamilyMember addFamily(FamilyMember familyMemberRequest) {
        FamilyMember familyMember =  familyMemberRepository.save(familyMemberRequest);
        return familyMember;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FamilyMemberDTO> getFamilyId(Long id) {
        List<FamilyMember> familyMembers = familyMemberRepository.findFamilyMemberByFamil_Id(id);
        List<FamilyMemberDTO> familyMemberDTOS = null;
        if (familyMembers != null) {
            familyMemberDTOS = familyMembers.stream().map( dto -> {
                FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
                familyMemberDTO.setId(dto.getId());
                familyMemberDTO.setDatetime(dto.getJoinAt());
                familyMemberDTO.setNickname(dto.getNickname());
                familyMemberDTO.setAvatarUrl(
                        dto.getUser() != null ? dto.getUser().getPicture() : null
                );
                familyMemberDTO.setRole(String.valueOf(dto.getId()));
                return familyMemberDTO;
            }).toList();
        }
        return familyMemberDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyMemberDTO getByFamilyAndUserId(Long familyId, Long userId) {
        Optional<FamilyMemberDTO> familyMemberDTO = redisService.getRedis(familyMemberKey + familyId, FamilyMemberDTO.class);
        if (familyMemberDTO.isPresent()) {
            return familyMemberDTO.get();
        }else{
            Optional<FamilyMember> familyMember = familyMemberRepository.findFamilyMemberByFamil_IdAndUser_Id(familyId, userId);
            if(familyMember.isPresent()){
                FamilyMemberDTO memberDTO  = new FamilyMemberDTO();
                memberDTO.setId(familyMember.get().getId());
                memberDTO.setNickname(familyMember.get().getNickname());
                memberDTO.setRole(String.valueOf(familyMember.get().getRoles()));

                redisService.saveRedis( familyMemberKey + familyId, memberDTO, Duration.ofDays(3));
                return memberDTO;
            }else{
                return null;
            }
        }
    }

    @Transactional
    @Override
    public String deleteFamilyMember(Long id) {
        FamilyMember familyMember = familyMemberRepository.findById(id).orElse(null);
        if (familyMember != null) {
            familyMemberRepository.delete(familyMember);
            redisService.deleteKey(familyMemberKey + familyMember.getFamil().getId());
            return "Deleted";
        }else {
            return  "Family Member Not Found";
        }
    }

    @Override
    public boolean existsMemberByFamilyId(Long familyId) {
        return familyMemberRepository.existsByFamil_Id(familyId);
    }

}
