package com.nsp.redsocial.model.dto.Publicacion;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDtoSimple;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PublicacionDto {
    private Long id;
    private UsuarioDtoSimple usuarioDtoSimple;
    private String titulo;
    private String contenido;
    private LocalDateTime fechaCreacion;
}
