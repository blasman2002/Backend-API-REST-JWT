package com.nsp.redsocial.model.dto.Usuario;

import lombok.*;

@Getter
@Builder
public class UsuarioPerfilDto {
    private Long id;
    private String nombreUsuario;
    private String descripcion;
}
