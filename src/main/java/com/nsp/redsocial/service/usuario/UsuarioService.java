package com.nsp.redsocial.service.usuario;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;

public interface UsuarioService {
    UsuarioDto save(UsuarioNuevoDto udto);
    UsuarioDto findById(Long id);
    void delete(Long id);
}
