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
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;

    private String email;

    private String contrasena;

    private String descripcion;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario1", fetch = FetchType.LAZY)
    private List<Estrella> estrellasDadas;

    @OneToMany(mappedBy = "usuario2", fetch = FetchType.LAZY)
    private List<Estrella> estrellasRecibidas;
}
