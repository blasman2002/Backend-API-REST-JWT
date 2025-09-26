package com.nsp.redsocial.model.dto.Usuario;

import lombok.*;

@Getter
@Builder
public class UsuarioNuevoDto {
    private String nombreUsuario;
    private String email;
    private String contrasena;
}
