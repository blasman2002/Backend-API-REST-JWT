package com.nsp.redsocial.model.dto.Publicacion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicacionNuevaDto {
    private String titulo;
    private String contenido;
}
