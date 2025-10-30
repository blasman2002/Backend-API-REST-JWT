package com.nsp.redsocial.model.repository;

import com.nsp.redsocial.model.entity.Like;
import com.nsp.redsocial.model.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    void deleteLikeById(LikeId likeId);
}
