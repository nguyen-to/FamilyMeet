package org.example.server.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class FamilyDTO {
    private Long id;
    private String familyName;
    private String familyCode;
    private LocalDate createAt;

    public FamilyDTO() {
    }

    public FamilyDTO(Long id, String familyName, String familyCode, LocalDate createAt) {
        this.id = id;
        this.familyName = familyName;
        this.familyCode = familyCode;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}
