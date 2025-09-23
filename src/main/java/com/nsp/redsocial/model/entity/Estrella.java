package com.nsp.redsocial.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "estrellas")
public class Estrella {
    @EmbeddedId
    private EstrellaId estrellaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuario1Id")
    @JoinColumn(name = "usuario1_id")
    private Usuario usuario1;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuario2Id")
    @JoinColumn(name = "usuario2_id")
    private Usuario usuario2;

    @Column(name = "fecha_estrella")
    private LocalDateTime fechaEstrella;
}
