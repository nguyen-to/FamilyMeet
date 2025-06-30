package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.GroupDTO;
import org.example.server.entity.GroupEntity;
import org.example.server.repository.GroupRepository;
import org.example.server.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Transactional
    @Override
    public GroupEntity addGroup(GroupEntity groupRequest) {
        GroupEntity newGroup = groupRepository.save(groupRequest);
        return newGroup;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDTO> groups(Long familyId) {
        List<GroupEntity> groupEntities = groupRepository.findByFamilyId(familyId);
        List<GroupDTO> groupDTOs = null;
        if (groupEntities != null) {
            groupDTOs = groupEntities.stream().map(dto -> {
                GroupDTO groupDTO = new GroupDTO();
                groupDTO.setId(dto.getId());
                groupDTO.setName(dto.getName());
                groupDTO.setDescription(dto.getDescription());
                groupDTO.setAvatarUrl(dto.getAvatarUrl());
                groupDTO.setCreatedAt(dto.getCreateAt());
                return groupDTO;
            }).toList();
        }
        return groupDTOs;
    }
}
