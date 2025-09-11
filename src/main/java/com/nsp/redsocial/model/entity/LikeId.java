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
public class LikeId implements Serializable {
    @Column(name = "publicacion_id")
    private Long publicacionId;

    @Column(name = "usuario_id")
    private Long usuarioId;
}
