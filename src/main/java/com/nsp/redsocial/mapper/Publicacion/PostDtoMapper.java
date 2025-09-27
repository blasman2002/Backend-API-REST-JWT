package com.nsp.redsocial.mapper.Publicacion;

import com.nsp.redsocial.mapper.Usuario.UserDtoMapper;
import com.nsp.redsocial.model.dto.Post.PostDto;
import com.nsp.redsocial.model.dto.User.UserDtoSimple;
import com.nsp.redsocial.model.entity.Post;
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

        UserDtoSimple u = userDtoMapper.toDtoSimple(post.getUser());

        return PostDto.builder()
                .id(post.getId())
                .userDtoSimple(u)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
