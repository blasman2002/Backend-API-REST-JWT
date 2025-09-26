package com.nsp.redsocial.mapper.Publicacion;

import com.nsp.redsocial.mapper.Usuario.UsuarioDtoMapper;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioDtoSimple;
import com.nsp.redsocial.model.entity.Publicacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PublicacionDtoMapper {

    private final UsuarioDtoMapper usuarioDtoMapper;

    public PublicacionDto toDto(Publicacion publicacion) {
        if (publicacion == null) {
            return null;
        }

        UsuarioDtoSimple u = usuarioDtoMapper.toDtoSimple(publicacion.getUsuario());

        return PublicacionDto.builder()
                .id(publicacion.getId())
                .usuarioDtoSimple(u)
                .titulo(publicacion.getTitulo())
                .contenido(publicacion.getContenido())
                .fechaCreacion(publicacion.getFechaCreacion())
                .build();
    }

}
