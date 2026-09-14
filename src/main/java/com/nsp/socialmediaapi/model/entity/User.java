package com.nsp.socialmediaapi.model.entity;

import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.enm.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String userPassword;

    private String userDesc;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private Status status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = Status.ACTIVE;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user1", fetch = FetchType.LAZY)
    private List<Star> starsGiven;

    @OneToMany(mappedBy = "user2", fetch = FetchType.LAZY)
    private List<Star> starsReceived;

}
