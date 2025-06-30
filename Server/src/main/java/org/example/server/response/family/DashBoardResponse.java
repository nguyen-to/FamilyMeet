package org.example.server.response.family;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.dto.FamilyDTO;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.dto.GroupDTO;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashBoardResponse {
    private FamilyDTO family;
    private List<FamilyMemberDTO> familyMembers;
    private List<GroupDTO> groups;
}
