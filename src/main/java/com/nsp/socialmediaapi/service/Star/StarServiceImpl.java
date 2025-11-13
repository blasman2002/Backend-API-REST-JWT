package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.exception.ResourceNotFoundException;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void giveStar(Long userIdGiver, Long userIdReceiver) {

        User userGiver  = userRepository.findById(userIdGiver).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userIdGiver)
        );

        User userReceiver = userRepository.findById(userIdReceiver).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userIdReceiver)
        );

        Star star = new Star();
        star.setUser1(userGiver);
        star.setUser2(userReceiver);

        starRepository.save(star);
    }

    @Override
    public void removeStar(Long userIdGiver, Long userIdReceiver) {
        StarId starId = new StarId(userIdGiver, userIdReceiver);

        Star star = starRepository.findById(starId).orElseThrow(
                () -> new ResourceNotFoundException("star not found")
        );

        starRepository.delete(star);
    }
}
