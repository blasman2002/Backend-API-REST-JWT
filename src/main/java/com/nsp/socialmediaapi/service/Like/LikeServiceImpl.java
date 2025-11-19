package com.nsp.socialmediaapi.service.Like;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.LikeAlreadyExistsException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.LikeNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.entity.Like;
import com.nsp.socialmediaapi.model.entity.LikeId;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.LikeRepository;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void giveLikeToPost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        Post post =  postRepository.findById(id).orElseThrow(
                PostNotFoundException::new
        );

        LikeId likeId = new LikeId();
        likeId.setUserId(user.getId());
        likeId.setPostId(post.getId());

        if (likeRepository.existsById(likeId)) {
            throw new LikeAlreadyExistsException();
        }

        Like like = new Like();
        like.setId(likeId);
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    @Override
    public void removeLikeFromPost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        LikeId likeId = new LikeId();
        likeId.setUserId(user.getId());
        likeId.setPostId(id);

        Like like = likeRepository.findById(likeId).orElseThrow(
                LikeNotFoundException::new
        );

        likeRepository.delete(like);
    }

}
