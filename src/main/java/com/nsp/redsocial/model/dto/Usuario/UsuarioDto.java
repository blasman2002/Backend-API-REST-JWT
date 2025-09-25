package com.nsp.redsocial.model.dto.Usuario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UsuarioDto {
    private Long id;
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String descripcion;
    private LocalDateTime fechaCreacion;
}
