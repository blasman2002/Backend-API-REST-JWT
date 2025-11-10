package com.nsp.socialmediaapi.mapper.Post;

import com.nsp.socialmediaapi.model.dto.Post.PostNewDto;
import com.nsp.socialmediaapi.model.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toNewPost(PostNewDto postNewDto) {
        if  (postNewDto == null) {
            return null;
        }

        return Post.builder()
                .title(postNewDto.getTitle())
                .content(postNewDto.getContent())
                .build();
    }

}
