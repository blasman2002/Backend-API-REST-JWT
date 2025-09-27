package com.nsp.redsocial.model.repository;

import com.nsp.redsocial.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
