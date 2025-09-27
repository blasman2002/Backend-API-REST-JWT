package com.nsp.redsocial.model.repository;

import com.nsp.redsocial.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getUserById(Long id);
}
