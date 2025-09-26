package com.nsp.redsocial.service.Publicacion;

import com.nsp.redsocial.mapper.Publicacion.PublicacionDtoMapper;
import com.nsp.redsocial.mapper.Publicacion.PublicacionMapper;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionDto;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionDtoSimple;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionNuevaDto;
import com.nsp.redsocial.model.entity.Publicacion;
import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.model.repository.PublicacionRepository;
import com.nsp.redsocial.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;

    private final UsuarioRepository usuarioRepository;

    private final PublicacionDtoMapper publicacionDtoMapper;

    private final PublicacionMapper publicacionMapper;

    @Override
    public PublicacionDto save(PublicacionNuevaDto pndto, Long usuarioId) {
        Publicacion p = publicacionMapper.nuevaPublicacion(pndto);
        Usuario u = usuarioRepository.getUsuarioById(usuarioId);
        p.setUsuario(u);

        Publicacion pGuardada = publicacionRepository.save(p);

        return publicacionDtoMapper.toDto(pGuardada);
    }

    @Override
    public List<PublicacionDtoSimple> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

}
