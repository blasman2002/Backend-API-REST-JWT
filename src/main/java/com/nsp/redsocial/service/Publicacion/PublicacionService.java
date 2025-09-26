package com.nsp.redsocial.service.Publicacion;

import com.nsp.redsocial.model.dto.Publicacion.PublicacionDto;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionDtoSimple;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionNuevaDto;

import java.util.List;

public interface PublicacionService {
    PublicacionDto save(PublicacionNuevaDto pndto, Long usuarioId);
    List<PublicacionDtoSimple> findAll();
    void delete(Long id);
}
