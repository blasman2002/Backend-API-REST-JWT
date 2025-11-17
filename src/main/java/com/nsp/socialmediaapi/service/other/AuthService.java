package com.nsp.socialmediaapi.service.other;

import com.nsp.socialmediaapi.exception.customExceptions.UserAlreadyExistsException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.mapper.User.UserMapper;
import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.dto.User.UserDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserDtoMapper userDtoMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserDto registerUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already in use");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User u = userMapper.toEntity(request);
        u.setRole(Role.USER);

        User saved = userRepository.save(u);

        return userDtoMapper.toDto(saved);
    }

    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getUserPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.generateTokenResponse(authentication);
    }

}
