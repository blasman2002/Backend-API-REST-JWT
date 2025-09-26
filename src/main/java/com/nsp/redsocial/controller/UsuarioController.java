package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.Usuario.UsuarioDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioNuevoDto;
import com.nsp.redsocial.model.dto.Usuario.UsuarioPerfilDto;
import com.nsp.redsocial.service.Usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("usuarios")
    public ResponseEntity<UsuarioDto> registrar(@RequestBody UsuarioNuevoDto udto) {
        return ResponseEntity.ok(usuarioService.save(udto));
    }

    @GetMapping("usuarios/perfil/{nombreUsuario}")
    public ResponseEntity<UsuarioPerfilDto> encontrarPorNombre(@PathVariable String nombreUsuario) {
        return ResponseEntity.ok(usuarioService.findByNombreUsuario(nombreUsuario));
    }

}
