package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.dto.UserPassUpdateDto;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Service
public class CheckService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final MappingUtils mappingUtils;
    private final MapSessions mapSessions;

    public CheckResponse getUser() {
        CheckResponse checkResponse = new CheckResponse();
        String sessionId = httpSession.getId();
        User user;

        if (mapSessions.isEmpty()) {
            user = null;
        } else {
            Long userId = mapSessions.getUserId(sessionId);
            user = userRepository.findById(userId).orElse(null);
        }

        if (user != null) {
            checkResponse.setResult(true);
            checkResponse.setUserLoginOutDto(mappingUtils.mapToUserLoginOutDto(user));
        } else {
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
        }
        return checkResponse;
    }
}