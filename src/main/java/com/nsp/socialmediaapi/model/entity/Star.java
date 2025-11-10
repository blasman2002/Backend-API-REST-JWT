package com.nsp.socialmediaapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stars")
public class Star {
    @EmbeddedId
    private StarId starId;

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
}
