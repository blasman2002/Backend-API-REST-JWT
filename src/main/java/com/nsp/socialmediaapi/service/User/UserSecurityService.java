package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService {

    private final UserService userService;

    public boolean isActive(Authentication authentication) {
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        return user.getStatus() == Status.ACTIVE;
    }

}
