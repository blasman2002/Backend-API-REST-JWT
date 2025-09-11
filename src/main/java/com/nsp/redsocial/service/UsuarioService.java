package com.nsp.redsocial.service;

import com.nsp.redsocial.model.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    Usuario findById(Long id);
    void delete(Usuario usuario);
}
