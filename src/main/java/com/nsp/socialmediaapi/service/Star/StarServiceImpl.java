package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfStarNotAllowedException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.StarNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void giveStar(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User starGiver = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        User starred = userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );

        if (starGiver.getId().equals(starred.getId())) {
            throw new SelfStarNotAllowedException();
        }

        StarId starId = new StarId(starGiver.getId(), starred.getId());

        if (starRepository.existsById(starId)) {
            throw new StarAlreadyExistsException();
        }

        Star star = new Star();
        star.setStarId(starId);
        star.setUser1(starGiver);
        star.setUser2(starred);

        starRepository.save(star);
    }

    @Override
    @Transactional
    public void removeStar(Long userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User starGiver = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        User starred = userRepository.findById(userId).orElseThrow(
                UserNotFoundException::new
        );

        StarId starId = new StarId();
        starId.setUser1id(starGiver.getId());
        starId.setUser2id(starred.getId());

        if (!starRepository.existsById(starId)) {
            throw new StarNotFoundException();
        }

        starRepository.deleteById(starId);
    }

}
