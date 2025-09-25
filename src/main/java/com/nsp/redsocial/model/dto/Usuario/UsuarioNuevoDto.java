package com.nsp.redsocial.model.dto.Usuario;

import lombok.*;

@Getter
@Setter
@Builder
public class UsuarioNuevoDto {
    private String nombreUsuario;
    private String email;
    private String contrasena;
}
