package org.example.server.service;

import org.example.server.dto.GroupDTO;
import org.example.server.entity.GroupEntity;

import java.util.List;

public interface GroupService {
    public GroupEntity addGroup(GroupEntity groupRequest);
    public List<GroupDTO> groups(Long familyId);
}
