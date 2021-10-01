package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;

@Repository
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(User user){
        userRepository.save(user);
    }

    public boolean isUser(String email){
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}


//    int moderationCount = postRepository.findByModerationStatus(ModerationStatus.NEW).size();