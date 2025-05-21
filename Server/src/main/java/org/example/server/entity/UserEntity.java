package org.example.server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "user_account",
indexes = {
        @Index(name = "idx_fullName",columnList = "fullname")
}
)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "picture")
    private String picture;
    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "create_at",updatable = false)
    private Timestamp createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles role;
}
