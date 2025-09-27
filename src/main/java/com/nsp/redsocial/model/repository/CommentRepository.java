package com.nsp.redsocial.model.repository;

import com.nsp.redsocial.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
