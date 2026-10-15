package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CommentMapper {

    @Mapping(target = "userDisplayDto", source = "user")
    CommentDto mapToCommentDto(Comment comment);

}
