package com.nsp.socialmediaapi.service.Auth;

import com.nsp.socialmediaapi.exception.customExceptions.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.customExceptions.UserAlreadyExistsException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.Login.RefreshTokenRequest;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public UserDto registerUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already in use");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User u = new User();
        u.setUsername(request.getUsername());
        u.setEmail(request.getEmail());
        u.setUserPassword(request.getUserPassword());
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

    public LoginResponseDto refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("invalid refresh token");
        }

        String user = jwtService.getUsernameFromToken(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if (userDetails == null) {
            throw new ResourceNotFoundException("User not found");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String accessToken = jwtService.generateAccessToken(authentication);

        return new LoginResponseDto(accessToken, refreshToken);
    }

}
