package ru.shvets.blog.services;

import com.github.cage.GCage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.models.CaptchaCode;
import ru.shvets.blog.repositories.CaptchaRepository;
import ru.shvets.blog.utils.TimeUtils;

import java.util.Base64;
import java.util.Date;

@Service
public class CaptchaService {
    private final CaptchaRepository captchaRepository;
    private final TimeUtils timeUtils;

    @Value("${captcha.timeHours}")
    private int timePeriodInHours;
    @Value("${captcha.lengthCaptcha}")
    private int lengthCaptcha;

    @Autowired
    public CaptchaService(CaptchaRepository captchaRepository,
                          TimeUtils timeUtils) {
        this.captchaRepository = captchaRepository;
        this.timeUtils = timeUtils;
    }

    public CaptchaDto getCaptcha() {
        final String PREFIX = "data:image/png;base64, ";

        CaptchaDto captchaDto = new CaptchaDto();
        GCage gCage = new GCage();
        String token = gCage.getTokenGenerator().next().substring(0, lengthCaptcha);
        String image = Base64.getEncoder().encodeToString(gCage.draw(token));

        captchaDto.setSecret(token);
        captchaDto.setImage(PREFIX.concat(image));

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setTime(new Date());
        captchaCode.setCode(token);
        captchaCode.setSecretCode("null"); // Что есть секретный код?
        captchaRepository.save(captchaCode);

        clearCaptcha();
        return captchaDto;
    }

    private void clearCaptcha() {
        long totalSeconds = (timePeriodInHours * 3600L) + timeUtils.getSecondsOffSet();
        captchaRepository.deleteAllTimeGreaterThanHour(totalSeconds);
    }
}