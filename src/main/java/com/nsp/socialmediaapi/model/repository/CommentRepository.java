package com.nsp.socialmediaapi.model.repository;

import com.nsp.socialmediaapi.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
