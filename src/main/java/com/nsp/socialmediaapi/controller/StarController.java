package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.service.Star.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class StarController {

    private final StarService starService;

    @PostMapping("{id}/stars")
    public ResponseEntity<?> giveStarToUser(@PathVariable Long id) {
        starService.giveStar(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("star given");
    }

    @DeleteMapping("{id}/stars")
    public ResponseEntity<?> removeStarFromUser(@PathVariable Long id) {
        starService.removeStar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/stars")
    public ResponseEntity<PageResponse<StarReceivedDto>> getStarsReceivedFromUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(starService.getUserStars(id, page, size));
    }

}
