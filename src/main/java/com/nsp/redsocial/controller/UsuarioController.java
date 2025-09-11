package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.entity.Usuario;
import com.nsp.redsocial.service.UsuarioService;
import org.springframework.http.HttpStatus;
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
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario actualizar(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @DeleteMapping("usuario/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        Usuario u = usuarioService.findById(id);
        usuarioService.delete(u);
    }

    @GetMapping("usuario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario encontrarPorId(@PathVariable Long id) {
        return usuarioService.findById(id);
    }
}
