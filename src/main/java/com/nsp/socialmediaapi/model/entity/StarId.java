package com.nsp.socialmediaapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class StarId implements Serializable {
    @Column(name = "user1_id")
    private Long user1id;

    @Column(name = "user2_id")
    private Long user2id;
}
