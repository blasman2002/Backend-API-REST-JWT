package com.nsp.redsocial.model.dto.Publicacion;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicacionDtoSimple {
    private Long id;
    private UsuarioDto usuarioDto;
    private String titulo;
}
