package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.dto.*;
import ru.shvets.blog.exceptions.CaptchaException;
import ru.shvets.blog.exceptions.TimeExpiredException;
import ru.shvets.blog.models.CaptchaCode;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.CaptchaRepository;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Repository
public class UserService {
    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;
    private final MappingUtils mappingUtils;
    private final HttpSession httpSession;
    private final MapSessions mapSessions;

    public User findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    public boolean isUser(String email) {
        return userRepository.existsByEmail(email);
    }

    public ErrorResponse addUser(NewUserDto newUserDto) {
        ErrorResponse response = new ErrorResponse();
        userRepository.save(mappingUtils.mapToUser(newUserDto));
        response.setResult(true);
        response.setErrors(null);
        return response;
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

    public ErrorResponse updateProfile(UserUpdatedDto userUpdatedDto) throws IOException {
        userRepository.save(mappingUtils.mapUserUpdatedDtoToUser(userUpdatedDto));

        ErrorResponse response = new ErrorResponse();
        response.setResult(true);
        response.setErrors(null);
        return response;
    }

    public ErrorResponse restoreUser(UserJustEmailDto userJustEmailDto) {
        ErrorResponse response = new ErrorResponse();

        if (userJustEmailDto.getEmail() == null) {
            response.setResult(false);
            response.setErrors(null);
            return response;
        }

        User user = userRepository.getUserByEmail(userJustEmailDto.getEmail());
        if (user != null) {
            String hash = UUID.randomUUID().toString().replace("-", "");
            user.setCode(hash);
            userRepository.save(user);
            log.info("Письмо со ссылкой на восстановление пароля направлено");
            response.setResult(true);
        } else {
            response.setResult(false);
        }
        response.setErrors(null);
        return response;
    }

    public ErrorResponse updatePassword(UserPassUpdateDto userPassUpdateDto) {
        ErrorResponse response = new ErrorResponse();
        User user = userRepository.getUserByCode(userPassUpdateDto.getCode());
        if (user == null) {
            throw new TimeExpiredException("Ссылка для восстановления пароля устарела");
        }

        CaptchaCode captchaCode = captchaRepository.getCaptchaCodeBySecretCode(userPassUpdateDto.getCaptchaSecret());
        if (captchaCode == null) {
            throw new CaptchaException("Каптча устарела");
        }

        if (userPassUpdateDto.getCaptcha().equals(captchaCode.getCode())) {
            user.setPassword(userPassUpdateDto.getPassword());
            userRepository.save(user);
        } else {
            throw new CaptchaException("Код с картинки введен неправильно");
        }
        response.setResult(true);
        response.setErrors(null);
        return response;
    }

    //Выход
    public ErrorResponse logout(){
        User user = mappingUtils.getUserFromListSessions();
        mapSessions.removeUser(mapSessions.getSessionId(user.getId()));
        return ErrorResponse.of(true, null);
    }
}