package org.example.server.entity;
import jakarta.persistence.*;
import lombok.Builder;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "familys")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "family_code")
    private String familyCode;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "create_at")
    private LocalDate createAt;

    public Family() {
    }

    public Family(Long id, String familyName, String familyCode, String avatarUrl, LocalDate createAt) {
        this.id = id;
        this.familyName = familyName;
        this.familyCode = familyCode;
        this.avatarUrl = avatarUrl;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
