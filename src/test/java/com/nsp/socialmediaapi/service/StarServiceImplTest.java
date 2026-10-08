package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfStarNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.StarNotFoundException;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Star.StarDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.service.Star.StarServiceImpl;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StarServiceImplTest {

    @Mock
    private PageMapper pageMapper;

    @Mock
    private StarDtoMapper starDtoMapper;

    @Mock
    private StarRepository starRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private StarServiceImpl starService;

    public User USER_PREPARED_GIVER = User.builder()
            .id(1L)
            .username("username2000")
            .role(Role.USER)
            .status(Status.ACTIVE)
            .build();

    public User USER_PREPARED_RECEIVER = User.builder()
            .id(2L)
            .username("username3000")
            .role(Role.USER)
            .status(Status.ACTIVE)
            .build();

    private void setAuthenticatedUser(User u) {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(u.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void giveStar_shouldSuccessfullyGiveStarToUser() {
        setAuthenticatedUser(USER_PREPARED_GIVER);

        when(userService.findByUsername(USER_PREPARED_GIVER.getUsername())).thenReturn(USER_PREPARED_GIVER);

        when(userService.findById(USER_PREPARED_RECEIVER.getId())).thenReturn(USER_PREPARED_RECEIVER);

        StarId starId = new StarId(USER_PREPARED_GIVER.getId(), USER_PREPARED_RECEIVER.getId());

        when(starRepository.existsById(starId)).thenReturn(false);

        starService.giveStar(USER_PREPARED_RECEIVER.getId());

        verify(userService).findByUsername(USER_PREPARED_GIVER.getUsername());
        verify(userService).findById(USER_PREPARED_RECEIVER.getId());
        verify(starRepository).existsById(starId);
        verify(starRepository).save(any(Star.class));
    }

    @Test
    void giveStar_shouldThrowException_whenReceiverIsSameAsGiver() {
        setAuthenticatedUser(USER_PREPARED_GIVER);

        when(userService.findByUsername(USER_PREPARED_GIVER.getUsername())).thenReturn(USER_PREPARED_GIVER);

        when(userService.findById(USER_PREPARED_GIVER.getId())).thenReturn(USER_PREPARED_GIVER);

        assertThrows(
                SelfStarNotAllowedException.class,
                () -> starService.giveStar(USER_PREPARED_GIVER.getId())
        );

    }

    @Test
    void giveStar_shouldThrowException_whenReceiverAlreadyStarredByGiver() {
        setAuthenticatedUser(USER_PREPARED_GIVER);

        when(userService.findByUsername(USER_PREPARED_GIVER.getUsername())).thenReturn(USER_PREPARED_GIVER);

        when(userService.findById(USER_PREPARED_RECEIVER.getId())).thenReturn(USER_PREPARED_RECEIVER);

        StarId starId = new StarId(USER_PREPARED_GIVER.getId(), USER_PREPARED_RECEIVER.getId());

        when(starRepository.existsById(starId)).thenReturn(true);

        assertThrows(
                StarAlreadyExistsException.class,
                () -> starService.giveStar(USER_PREPARED_RECEIVER.getId())
        );

        verify(starRepository).existsById(starId);
        verify(starRepository, never()).save(any(Star.class));
    }

    @Test
    void removeStar_shouldSuccessfullyRemoveStar() {
        setAuthenticatedUser(USER_PREPARED_GIVER);

        when(userService.findByUsername(USER_PREPARED_GIVER.getUsername())).thenReturn(USER_PREPARED_GIVER);

        when(userService.findById(USER_PREPARED_RECEIVER.getId())).thenReturn(USER_PREPARED_RECEIVER);

        StarId starId = new StarId(USER_PREPARED_GIVER.getId(), USER_PREPARED_RECEIVER.getId());

        when(starRepository.existsById(starId)).thenReturn(true);

        starService.removeStar(USER_PREPARED_RECEIVER.getId());

        verify(starRepository).deleteById(starId);
    }

    @Test
    void removeStar_shouldThrowException_whenReceiverIsNotStarredByGiver() {
        setAuthenticatedUser(USER_PREPARED_GIVER);

        when(userService.findByUsername(USER_PREPARED_GIVER.getUsername())).thenReturn(USER_PREPARED_GIVER);

        when(userService.findById(USER_PREPARED_RECEIVER.getId())).thenReturn(USER_PREPARED_RECEIVER);

        StarId starId = new StarId(USER_PREPARED_GIVER.getId(), USER_PREPARED_RECEIVER.getId());

        when(starRepository.existsById(starId)).thenReturn(false);

        assertThrows(
                StarNotFoundException.class,
                () -> starService.removeStar(USER_PREPARED_RECEIVER.getId())
        );

    }

    @Test
    void getUserStars_shouldSuccessfullyReturnUserStars() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("createdAt").descending());

        Star star = mock(Star.class);

        StarReceivedDto starReceivedDto = mock(StarReceivedDto.class);

        Page<Star> starsPage = new PageImpl<>(List.of(star), pageable, 1);

        Page<StarReceivedDto> starsDtoPage = new PageImpl<>(List.of(starReceivedDto), pageable, 1);

        PageResponse<StarReceivedDto> expectedResponse = mock(PageResponse.class);

        when(starRepository.findByUser1_Id(USER_PREPARED_GIVER.getId(), pageable)).thenReturn(starsPage);
        when(starDtoMapper.toStarReceiverDto(star)).thenReturn(starReceivedDto);
        when(pageMapper.toPageResponse(starsDtoPage)).thenReturn(expectedResponse);

        PageResponse<StarReceivedDto> result = starService.getUserStars(USER_PREPARED_GIVER.getId(), 1, 10);

        assertSame(expectedResponse, result);

        verify(starRepository).findByUser1_Id(USER_PREPARED_GIVER.getId(), pageable);
        verify(starDtoMapper).toStarReceiverDto(star);
        verify(pageMapper).toPageResponse(starsDtoPage);

    }

}
