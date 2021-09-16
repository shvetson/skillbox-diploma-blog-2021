package ru.shvets.blog.services;

import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.models.CaptchaCode;
import ru.shvets.blog.repositories.CaptchaRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaService {
    @Autowired
    private CaptchaRepository captchaRepository;
    @Value("${captcha.timeHours}")
    private int timePeriodInHours;
    @Value("${captcha.lengthSecretCode}")
    private int lengthSecretCode;
    @Value("${captcha.alphabet}")
    private String alphabet;

    public CaptchaDto getCaptcha() {
        final String PREFIX = "data:image/png;base64, ";

        CaptchaDto captchaDto = new CaptchaDto();
        GCage gCage = new GCage();
        String token = gCage.getTokenGenerator().next();
        String image = Base64.getEncoder().encodeToString(gCage.draw(token));

        captchaDto.setSecret(token);
        captchaDto.setImage(PREFIX.concat(image));

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setTime(new Date());
        captchaCode.setSecretCode(getSecretCode(lengthSecretCode));
        captchaCode.setCode(token);
        captchaRepository.save(captchaCode);

        clearCaptcha();
        return captchaDto;
    }

    private void clearCaptcha(){
        ZoneId z = ZoneId.systemDefault();
        int offSetSeconds = z.getRules().getOffset(Instant.now()).getTotalSeconds();
        int totalSeconds= (timePeriodInHours * 3600) + offSetSeconds;
        captchaRepository.deleteAllTimeGreaterThanHour(totalSeconds);
    }

    private String getSecretCode(int length) {
        Random random = new Random();
        int countSymbols = alphabet.length();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(alphabet.charAt(random.nextInt(countSymbols - 1) + 1));
        }
        return code.toString();
    }
}