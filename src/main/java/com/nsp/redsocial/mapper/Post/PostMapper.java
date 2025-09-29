package com.nsp.redsocial.mapper.Post;

import com.nsp.redsocial.model.dto.Post.PostNewDto;
import com.nsp.redsocial.model.entity.Post;
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
