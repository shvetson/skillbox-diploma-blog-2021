package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.dto.UserLoginInDto;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Slf4j
@Repository
public class UserService {
    private final UserRepository userRepository;
    private final MappingUtils mappingUtils;
    private final HttpSession httpSession;
    private final MapSessions mapSessions;

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
        CheckResponse checkResponse = new CheckResponse();

        User user = userRepository.findUserByEmailAndPassword(userLoginInDto.getEmail(), userLoginInDto.getPassword());

        if (user != null) {
            checkResponse.setResult(true);
            checkResponse.setUserLoginOutDto(mappingUtils.mapToUserLoginOutDto(user));

            String sessionId = httpSession.getId();
            Long userId = user.getId();
            mapSessions.addData(sessionId, userId);

            log.info("Запись в лист сессий внесена");
            log.info("ID  пользователя - " + userId + ", ID сессии - " + sessionId);
        } else {
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
        }
        return checkResponse;
    }

    public User findUserById(Long userId){
        return userRepository.findUserById(userId);
    }
}