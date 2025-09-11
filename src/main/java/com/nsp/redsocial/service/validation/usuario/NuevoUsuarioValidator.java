package com.nsp.redsocial.service.validation.usuario;

import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.model.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class NuevoUsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public NuevoUsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validate(Usuario u) {
        if (u == null) {
            throw new IllegalArgumentException("NO USUARIO.");
        }


    }
}
