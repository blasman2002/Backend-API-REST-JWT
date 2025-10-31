package com.nsp.redsocial.mapper.Comment;

import com.nsp.redsocial.mapper.User.UserDtoMapper;
import com.nsp.redsocial.model.dto.Comment.CommentDto;
import com.nsp.redsocial.model.dto.User.UserDtoSimple;
import com.nsp.redsocial.model.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoMapper {

    private final UserDtoMapper userDtoMapper;

    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        UserDtoSimple uDtoSi = userDtoMapper.toDtoSimple(comment.getUser());

        return CommentDto.builder()
                .id(comment.getId())
                .user(uDtoSi)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
