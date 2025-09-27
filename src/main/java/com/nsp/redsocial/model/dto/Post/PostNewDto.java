package com.nsp.redsocial.model.dto.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostNewDto {
    private String title;
    private String content;
}
