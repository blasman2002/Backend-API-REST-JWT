package com.nsp.redsocial.mapper.Usuario;

import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toNuevoUsuario(UsuarioNuevoDto udto) {
        if (udto == null) {
            return null;
        }

        return Usuario.builder()
                .nombreUsuario(udto.getNombreUsuario())
                .email(udto.getEmail())
                .contrasena(udto.getContrasena())
                .build();
    }

}
