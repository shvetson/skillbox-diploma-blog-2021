package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;

@Repository
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(User user){
        userRepository.save(user);
    }
}