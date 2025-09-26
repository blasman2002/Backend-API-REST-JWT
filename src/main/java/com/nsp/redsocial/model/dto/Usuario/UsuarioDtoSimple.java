package com.nsp.redsocial.model.dto.Usuario;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioDtoSimple {
    private Long id;
    private String nombreUsuario;
}
