package com.nsp.socialmediaapi.mapper.Post;

import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostDtoMapper {

    private final UserDtoMapper userDtoMapper;

    public PostDto toDto(Post post) {
        if (post == null) {
            return null;
        }

        UserDisplayDto u = userDtoMapper.toUserDisplay(post.getUser());

        return PostDto.builder()
                .id(post.getId())
                .userDisplayDto(u)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public PostDtoSimple toDtoSimple(Post post) {
        if (post == null) {
            return null;
        }

        UserDisplayDto u = userDtoMapper.toUserDisplay(post.getUser());

        return PostDtoSimple.builder()
                .id(post.getId())
                .userDisplayDto(u)
                .title(post.getTitle())
                .build();
    }

}
