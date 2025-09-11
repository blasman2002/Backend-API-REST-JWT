package com.nsp.redsocial.model.entity;

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
public class EstrellaId implements Serializable {
    @Column(name = "usuario1_id")
    private Long usuario1Id;

    @Column(name = "usuario2_id")
    private Long usuario2Id;
}
