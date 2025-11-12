package com.nsp.socialmediaapi.model.repository;

import com.nsp.socialmediaapi.model.entity.Like;
import com.nsp.socialmediaapi.model.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,LikeId> {

}
