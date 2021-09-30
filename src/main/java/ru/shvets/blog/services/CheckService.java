package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.UserResponse;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.PostRepository;
import ru.shvets.blog.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
@AllArgsConstructor
public class CheckService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HttpSession httpSession;

    public CheckResponse getUser(long id) {
        boolean isAuth = new Random().nextBoolean();

        if (!isAuth) {
            httpSession.setAttribute("user", (long) 0);
            return new CheckResponse(false, null);
        }

        UserResponse userResponse = new UserResponse();
        User user = userRepository.findById(id).orElse(null);
        int moderationCount = postRepository.findByModerationStatus(ModerationStatus.NEW).size();

        if (user != null) {
            userResponse.setId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setPhoto(user.getPhoto());
            userResponse.setEmail(user.getEmail());
            userResponse.setModeration(user.getIsModerator() == 1);
            userResponse.setModerationCount(user.getIsModerator() == 1 ? moderationCount : 0);
            userResponse.setSettings(true);

            httpSession.setAttribute("user", user.getId());
        }

        return new CheckResponse(true, userResponse);
    }
}