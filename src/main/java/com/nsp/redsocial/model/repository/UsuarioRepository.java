package com.nsp.redsocial.model.repository;

import com.nsp.redsocial.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
