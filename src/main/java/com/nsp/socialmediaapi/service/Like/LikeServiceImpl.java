package com.nsp.socialmediaapi.service.Like;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.LikeAlreadyExistsException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.LikeNotFoundException;
import com.nsp.socialmediaapi.model.entity.Like;
import com.nsp.socialmediaapi.model.entity.LikeId;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.LikeRepository;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final PostService postService;

    private final UserService userService;

    private final LikeRepository likeRepository;

    @Override
    @PreAuthorize("@userSecurityService.isActive(authentication)")
    @Transactional
    public void giveLikeToPost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

        Post post =  postService.findById(id);

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
    @Transactional
    public void removeLikeFromPost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

        LikeId likeId = new LikeId();
        likeId.setUserId(user.getId());
        likeId.setPostId(id);

        Like like = likeRepository.findById(likeId).orElseThrow(
                LikeNotFoundException::new
        );

        likeRepository.delete(like);
    }

}
