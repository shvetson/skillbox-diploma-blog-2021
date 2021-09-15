package ru.shvets.blog.services;

import com.github.cage.Cage;
import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.models.CaptchaCode;
import ru.shvets.blog.repositories.CaptchaRepository;

import java.util.Base64;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class CaptchaService {
    private final CaptchaRepository captchaRepository;

    public CaptchaDto getCaptcha() {
        final String PREFIX = "data:image/png;base64, ";

        CaptchaDto captchaDto = new CaptchaDto();
        Cage gCage = new GCage();

        String token = gCage.getTokenGenerator().next();
        String image = Base64.getEncoder().encodeToString(gCage.draw(token));

        captchaDto.setSecret(token);
        captchaDto.setImage(PREFIX.concat(image));

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setTime(new Date());
        captchaCode.setSecretCode(getSecretCode(10));
        captchaCode.setCode(token);
        captchaRepository.save(captchaCode);

        captchaRepository.deleteAllTimeGreaterThanHour();
        return captchaDto;
    }

    public String getSecretCode(int length) {
        Random random = new Random();
        String alphabet = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(alphabet.charAt(random.nextInt(alphabet.length()-1) + 1));
        }
        return code.toString();
    }
}