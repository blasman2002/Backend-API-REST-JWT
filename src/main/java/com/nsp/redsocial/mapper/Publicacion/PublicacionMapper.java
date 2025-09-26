package com.nsp.redsocial.mapper.Publicacion;

import com.nsp.redsocial.model.dto.Publicacion.PublicacionNuevaDto;
import com.nsp.redsocial.model.entity.Publicacion;
import org.springframework.stereotype.Component;

@Component
public class PublicacionMapper {

    public Publicacion nuevaPublicacion(PublicacionNuevaDto pndto) {
        if  (pndto == null) {
            return null;
        }

        return Publicacion.builder()
                .titulo(pndto.getTitulo())
                .contenido(pndto.getContenido())
                .build();
    }

}
