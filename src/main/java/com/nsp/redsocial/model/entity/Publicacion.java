package com.nsp.redsocial.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "publicaciones")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String titulo;

    private String contenido;

    private Timestamp fechaCreacion;

    @OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;
}
