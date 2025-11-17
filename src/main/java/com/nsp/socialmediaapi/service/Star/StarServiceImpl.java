package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.exception.customExceptions.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.customExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void giveStar(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User starGiver = userRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException("Authenticated user not found")
        );

        User starred = userRepository.findById(id).orElseThrow(
                () -> new  ResourceNotFoundException("User not found")
        );

        if (starGiver.getId().equals(starred.getId())) {
            throw new IllegalArgumentException("cant star logged user");
        }

        StarId starId = new StarId(starGiver.getId(), starred.getId());

        if (starRepository.existsById(starId)) {
            throw new StarAlreadyExistsException("user already starred");
        }

        Star star = new Star();
        star.setStarId(starId);
        star.setUser1(starGiver);
        star.setUser2(starred);

        starRepository.save(star);
    }

    @Override
    public void removeStar(Long userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User starGiver = userRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException("Authenticated user not found")
        );

        User starred = userRepository.findById(userId).orElseThrow(
                () -> new  ResourceNotFoundException("User not found")
        );

        if (starGiver.getId().equals(starred.getId())) {
            throw new IllegalArgumentException("cant remove star from logged user");
        }

        StarId starId = new StarId();
        starId.setUser1id(starGiver.getId());
        starId.setUser2id(starred.getId());

        if (!starRepository.existsById(starId)) {
            throw new ResourceNotFoundException("star not found");
        }

        starRepository.deleteById(starId);
    }
}
