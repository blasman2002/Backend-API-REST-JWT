package com.nsp.redsocial.mapper.Usuario;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioPerfilDto;
import com.nsp.redsocial.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoMapper {

    public UsuarioPerfilDto toUsuarioPerfilDto(Usuario u) {
        if (u == null) {
            return null;
        }

        return UsuarioPerfilDto.builder()
                .nombreUsuario(u.getNombreUsuario())
                .descripcion(u.getDescripcion())
                .build();

    }

    public UsuarioDto toUsuarioDto(Usuario u) {
        if (u == null) {
            return null;
        }

        return UsuarioDto.builder()
                .id(u.getId())
                .nombreUsuario(u.getNombreUsuario())
                .email(u.getEmail())
                .contrasena(u.getContrasena())
                .descripcion(u.getDescripcion())
                .fechaCreacion(u.getFechaCreacion())
                .build();
    }

}
