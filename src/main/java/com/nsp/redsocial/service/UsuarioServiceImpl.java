package com.nsp.redsocial.service;

import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.model.repository.UsuarioRepository;
import com.nsp.redsocial.service.validation.usuario.NuevoUsuarioValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final NuevoUsuarioValidator nuevoUsuarioValidator;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, NuevoUsuarioValidator nuevoUsuarioValidator) {
        this.usuarioRepository = usuarioRepository;
        this.nuevoUsuarioValidator = nuevoUsuarioValidator;
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
}
