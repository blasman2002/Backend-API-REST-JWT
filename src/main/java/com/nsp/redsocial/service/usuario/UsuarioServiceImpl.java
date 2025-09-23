package com.nsp.redsocial.service.usuario;

import com.nsp.redsocial.mapper.Usuario.UsuarioDtoMapper;
import com.nsp.redsocial.mapper.Usuario.UsuarioMapper;
import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public UsuarioDto save(UsuarioNuevoDto udto) {
        Usuario u = UsuarioMapper.toNuevoUsuario(udto);
        Usuario uGuardado = usuarioRepository.save(u);
        return UsuarioDtoMapper.toUsuarioDto(uGuardado);
    }

    @Transactional
    @Override
    public UsuarioDto findById(Long id) {
        return UsuarioDtoMapper.toUsuarioDto(usuarioRepository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public void delete(Long id) {

    }

}
