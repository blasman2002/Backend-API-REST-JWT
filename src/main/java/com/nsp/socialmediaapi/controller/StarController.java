package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Star.StarDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.service.Star.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class StarController {

    private final StarService starService;

    private final StarDtoMapper starDtoMapper;

    private final PageMapper pageMapper;

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
        Page<Star> userStars = starService.getStarsReceivedFromUser(id, page, size);
        Page<StarReceivedDto> userStarsDto = userStars.map(starDtoMapper::toStarReceiverDto);
        PageResponse<StarReceivedDto> userStarsDtoResponse = pageMapper.toPageResponse(userStarsDto);
        return ResponseEntity.ok(userStarsDtoResponse);
    }

}
