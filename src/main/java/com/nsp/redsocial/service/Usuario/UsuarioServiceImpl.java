package com.nsp.redsocial.service.Usuario;

import com.nsp.redsocial.mapper.Usuario.UsuarioDtoMapper;
import com.nsp.redsocial.mapper.Usuario.UsuarioMapper;
import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioPerfilDto;
import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final UsuarioDtoMapper usuarioDtoMapper;

    @Override
    public UsuarioDto save(UsuarioNuevoDto udto) {
        Usuario u = usuarioMapper.nuevoUsuario(udto);
        Usuario uGuardado = usuarioRepository.save(u);
        return usuarioDtoMapper.toDto(uGuardado);
    }

    @Override
    public UsuarioDto findById(Long id) {
        return usuarioDtoMapper.toDto(usuarioRepository.findById(id).orElse(null));
    }

    @Override
    public UsuarioPerfilDto findByNombreUsuario(String nombreUsuario) {
        Usuario u = usuarioRepository.findByNombreUsuario(nombreUsuario);
        return usuarioDtoMapper.toPerfilDto(u);
    }

    @Override
    public void delete(Long id) {

    }

}