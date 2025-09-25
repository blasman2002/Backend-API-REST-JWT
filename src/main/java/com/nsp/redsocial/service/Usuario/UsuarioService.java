package com.nsp.redsocial.service.Usuario;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioPerfilDto;

public interface UsuarioService {
    UsuarioDto save(UsuarioNuevoDto udto);
    UsuarioDto findById(Long id);
    UsuarioPerfilDto findByNombreUsuario(String nombreUsuario);
    void delete(Long id);
}
