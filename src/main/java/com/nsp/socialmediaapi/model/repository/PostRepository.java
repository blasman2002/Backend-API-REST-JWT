package com.nsp.socialmediaapi.model.repository;

import com.nsp.socialmediaapi.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
