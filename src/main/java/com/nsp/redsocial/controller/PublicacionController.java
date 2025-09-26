package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.Publicacion.PublicacionDto;
import com.nsp.redsocial.model.dto.Publicacion.PublicacionNuevaDto;
import com.nsp.redsocial.service.Publicacion.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PublicacionController {
    private final PublicacionService publicacionService;

    @PostMapping("/usuarios/{usuarioId}/publicaciones")
    public ResponseEntity<PublicacionDto> createPublicacion(@PathVariable Long usuarioId, @RequestBody PublicacionNuevaDto publicacionNuevaDto) {
        return ResponseEntity.ok(publicacionService.save(publicacionNuevaDto, usuarioId));
    }

}
