package com.nsp.socialmediaapi.service.Like;

import com.nsp.socialmediaapi.exception.ResourceNotFoundException;
import com.nsp.socialmediaapi.model.entity.Like;
import com.nsp.socialmediaapi.model.entity.LikeId;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.LikeRepository;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void giveLike(Long postId, Long userId) {
        LikeId likeId = new LikeId();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        likeId.setPostId(postId);
        likeId.setUserId(userId);

        Like like = new Like();
        like.setId(likeId);
        like.setPost(post);
        like.setUser(user);

        likeRepository.save(like);
    }

    @Override
    public void removeLike(Long postId, Long userId) {
        LikeId likeId = new LikeId(postId, userId);

        Like like = likeRepository.findById(likeId).orElseThrow(
                () -> new ResourceNotFoundException("like not found")
        );

        likeRepository.delete(like);
    }

}
