package com.nsp.socialmediaapi.service.Auth;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.EmailAlreadyInUseException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UsernameAlreadyInUseException;
import com.nsp.socialmediaapi.exception.SecurityException.InvalidRefreshTokenException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.Login.RefreshTokenRequest;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.dto.User.UserDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        User u = new User();
        u.setUsername(request.getUsername());
        u.setEmail(request.getEmail());
        u.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
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
            throw new InvalidRefreshTokenException();
        }

        String user = jwtService.getUsernameFromToken(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        String accessToken = jwtService.generateAccessToken(authentication);

        return new LoginResponseDto(accessToken, refreshToken);
    }

}
