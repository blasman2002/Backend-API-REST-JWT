package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfStarNotAllowedException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.StarNotFoundException;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;

    private final UserService userService;

    @Override
    @Transactional
    public void giveStar(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User starGiver = userService.findByUsername(currentUsername);

        User starred = userService.findById(id);

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

        User starGiver = userService.findByUsername(currentUsername);

        User starred = userService.findById(userId);

        StarId starId = new StarId();
        starId.setUser1id(starGiver.getId());
        starId.setUser2id(starred.getId());

        if (!starRepository.existsById(starId)) {
            throw new StarNotFoundException();
        }

        starRepository.deleteById(starId);
    }

    @Override
    @Transactional
    public Page<Star> getStarsReceivedFromUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return starRepository.findByUser1_Id(userId, pageable);
    }

}
