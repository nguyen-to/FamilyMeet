package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.entity.FamilyMember;
import org.example.server.repository.FamilyMemberRepository;
import org.example.server.request.FamilyRequest;
import org.example.server.service.FamilyMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyMemberServiceImpl implements FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;

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

}
