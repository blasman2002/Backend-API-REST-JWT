package com.nsp.socialmediaapi.model.repository;

import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, StarId> {
    Page<Star> findByUser1_Id(Long user1Id, Pageable pageable);
    Long countByUser2_Id(Long user2Id);
    Page<Star> findByUser2_Id(Long user2Id, Pageable pageable);
}
