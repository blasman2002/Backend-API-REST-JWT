package com.nsp.socialmediaapi.mapper.Comment;

import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.entity.Comment;
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

        UserDisplayDto uDtoSi = userDtoMapper.toUserDisplay(comment.getUser());

        return CommentDto.builder()
                .id(comment.getId())
                .user(uDtoSi)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
