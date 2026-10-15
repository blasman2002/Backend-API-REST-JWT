package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PostMapper {

    @Mapping(target = "userDisplayDto", source = "user")
    PostDto mapToPostDto(Post post);

    @Mapping(target = "userDisplayDto", source = "user")
    PostDtoSimple mapToPostDtoSimple(Post post);

}
