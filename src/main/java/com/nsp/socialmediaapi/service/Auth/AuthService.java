package com.nsp.socialmediaapi.service.Auth;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.EmailAlreadyInUseException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UsernameAlreadyInUseException;
import com.nsp.socialmediaapi.exception.SecurityException.InvalidRefreshTokenException;
import com.nsp.socialmediaapi.exception.SecurityException.InvalidLoginCredentialsException;
import com.nsp.socialmediaapi.exception.SecurityException.UserDisabledException;
import com.nsp.socialmediaapi.model.request.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.request.Login.RefreshTokenRequest;
import com.nsp.socialmediaapi.model.request.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(CreateUserRequest request) {
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

        userRepository.save(u);
    }

    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getUserPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtService.generateTokenResponse(authentication);
        } catch (BadCredentialsException e) {
            throw new InvalidLoginCredentialsException();
        } catch (DisabledException e) {
            throw new UserDisabledException();
        }
    }

    public LoginResponseDto refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.isValidRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String username = jwtService.getUsernameFromToken(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!userDetails.isEnabled()) {
            throw new UserDisabledException();
        }

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
