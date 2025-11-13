package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Star.StarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StarController {

    private final StarService starService;

    @PostMapping("users/{userIdReceiver}/stars/{userIdGiver}")
    public ResponseEntity<?> giveStar(@PathVariable Long userIdReceiver, @PathVariable Long userIdGiver) {
        starService.giveStar(userIdGiver,userIdReceiver);
        return ResponseEntity.status(HttpStatus.CREATED).body("star given");
    }

    @DeleteMapping("users/{userIdReceiver}/stars/{userIdGiver}")
    public ResponseEntity<?> deleteStar(@PathVariable Long userIdReceiver, @PathVariable Long userIdGiver) {
        starService.removeStar(userIdGiver,userIdReceiver);
        return ResponseEntity.ok("star deleted");
    }

}
