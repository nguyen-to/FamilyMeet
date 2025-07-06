package org.example.server.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatJoinedGroup {
    private Long groupId;
    private String userEmail;
    private String content;

    public ChatJoinedGroup() {
    }

    public ChatJoinedGroup(Long groupId, String userEmail, String content) {
        this.groupId = groupId;
        this.userEmail = userEmail;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
