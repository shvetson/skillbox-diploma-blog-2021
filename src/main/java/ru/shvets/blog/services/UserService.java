package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.dto.UserLoginInDto;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Repository
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MappingUtils mappingUtils;
    private final HttpSession httpSession;

    public ErrorResponse addUser(NewUserDto newUserDto) {
        ErrorResponse response = new ErrorResponse();
        userRepository.save(mappingUtils.mapToUser(newUserDto));
        response.setResult(true);
        response.setErrors(null);
        return response;
    }

    public boolean isUser(String email) {
        return userRepository.existsByEmail(email);
    }

    public CheckResponse getUserByLogin(UserLoginInDto userLoginInDto) {
        Map<String, Long> mapSessions = new HashMap<>();
        CheckResponse checkResponse = new CheckResponse();

        User user = userRepository.findUserByEmailAndPassword(userLoginInDto.getEmail(), userLoginInDto.getPassword());

        if (user != null) {
            mapSessions.put(httpSession.getId(), user.getId());
            checkResponse.setResult(true);
            checkResponse.setUserLoginOutDto(mappingUtils.mapToUserLoginOutDto(user));
        } else {
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
        }
        return checkResponse;
    }
}