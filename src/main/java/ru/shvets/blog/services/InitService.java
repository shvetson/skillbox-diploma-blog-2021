package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class InitService {
    private final UserRepository userRepository;

    public Long authId() {
        Random randomId = new Random();
        List<Long> userIdListAuth = new ArrayList<>();

        userRepository.findAll().forEach(user -> userIdListAuth.add(user.getId()));
        return userIdListAuth.get(randomId.nextInt(userIdListAuth.size()));
    }
}
