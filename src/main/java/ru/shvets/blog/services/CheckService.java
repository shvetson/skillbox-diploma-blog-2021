package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.UserRepository;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
@AllArgsConstructor
public class CheckService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final MappingUtils mappingUtils;

    public CheckResponse getUser(long id) {
        boolean isAuth = new Random().nextBoolean();
        CheckResponse checkResponse = new CheckResponse();

        if (!isAuth) {
            httpSession.setAttribute("user", (long) 0);
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
            return checkResponse;
        }

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            httpSession.setAttribute("user", user.getId());
            checkResponse.setResult(true);
            checkResponse.setUserLoginOutDto(mappingUtils.mapToUserLoginOutDto(user));
        } else {
            checkResponse.setResult(false);
            checkResponse.setUserLoginOutDto(null);
        }
        return checkResponse;
    }
}