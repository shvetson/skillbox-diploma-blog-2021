package ru.shvets.blog.services;

import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.repositories.CaptchaRepository;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
@AllArgsConstructor
public class CaptchaService {
    private final CaptchaRepository captchaRepository;

    public CaptchaDto getCaptcha(){
        final String PREFIX = "data:image/png;base64, ";

        CaptchaDto captchaDto = new CaptchaDto();
        GCage gCage = new GCage();

        String token = gCage.getTokenGenerator().next();
        String image = Base64.getEncoder().encodeToString(token.getBytes());

        captchaDto.setSecret(token);
        captchaDto.setImage(PREFIX.concat(image));

        return captchaDto;
    }
}