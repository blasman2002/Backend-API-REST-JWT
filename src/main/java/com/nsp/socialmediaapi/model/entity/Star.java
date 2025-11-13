package com.nsp.socialmediaapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stars")
public class Star {
    @EmbeddedId
    private StarId starId = new StarId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user1id")
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user2id")
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
