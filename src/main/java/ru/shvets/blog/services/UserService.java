package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.repositories.UserRepository;

@Repository
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public NewUserDto addUser(){
        NewUserDto dto = new NewUserDto();

        return dto;
    }
}