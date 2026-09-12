package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final PostService postService;

    private final UserDtoMapper userDtoMapper;

    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> showUserProfile(@PathVariable Long id) {
        User foundUser = userService.findById(id);
        UserProfileDto userProfileDto = userDtoMapper.toProfileDto(foundUser);

        return ResponseEntity.ok(userProfileDto);
    }

    @GetMapping("{id}/posts")
    public ResponseEntity<PageResponse<PostDtoSimple>> showUserPosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.showUserPosts(id, page, size));
    }

    @DeleteMapping("me")
    public ResponseEntity<?> deleteUserSelf() {
        userService.deleteUserSelf();
        return ResponseEntity.noContent().build();
    }

}
