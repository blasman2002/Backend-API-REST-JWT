package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.service.usuario.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioDto> crear(@RequestBody UsuarioNuevoDto udto) {
        return ResponseEntity.ok(usuarioService.save(udto));
    }



}
