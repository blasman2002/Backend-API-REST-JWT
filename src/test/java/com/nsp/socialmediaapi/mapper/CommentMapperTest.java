package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.Comment;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void mapperShouldCorrectlyMapCommentDto() {

        Post post = new Post();

        User user = User.builder()
                .id(1L)
                .username("username")
                .userPassword("password")
                .role(Role.USER)
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .post(post)
                .user(user)
                .content("text")
                .build();

        CommentDto result = commentMapper.mapToCommentDto(comment);

        assertEquals(result.getId(), comment.getId());
        assertEquals(result.getContent(), comment.getContent());
        assertEquals(result.getContent(), comment.getContent());

        assertNotNull(result.getUserDisplayDto());
        assertEquals(result.getUserDisplayDto().getId(), comment.getUser().getId());
        assertEquals(result.getUserDisplayDto().getUsername(), comment.getUser().getUsername());
    }

}
