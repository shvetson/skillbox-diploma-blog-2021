package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class CheckService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final MappingUtils mappingUtils;
    private final MapSessions mapSessions;

    public CheckResponse getUser(long id) {
        CheckResponse checkResponse = new CheckResponse();

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            checkResponse.setResult(true);
            checkResponse.setUserLoginOutDto(mappingUtils.mapToUserLoginOutDto(user));

            String sessionId = httpSession.getId();
            Long userId = id;
            mapSessions.addData(sessionId, userId);
        } else {
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
        }
        return checkResponse;
    }
}