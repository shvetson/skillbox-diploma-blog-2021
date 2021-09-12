package ru.shvets.blog.services;

import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.repositories.CaptchaRepository;

import java.awt.image.BufferedImage;

@Service
@AllArgsConstructor
public class CaptchaService {
    private final CaptchaRepository captchaRepository;

    public CaptchaDto getCaptcha(){
        CaptchaDto captchaDto = new CaptchaDto();

        GCage gCage = new GCage();

//        response.setContentType("image/"+gCage.getFormat());
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.setHeader("Progma", "no-cache");
//        response.setDateHeader("Max-Age", 0);

        String token = gCage.getTokenGenerator().next();
        BufferedImage image = gCage.drawImage(token);
        String imageStr = image.toString();

        System.out.println(token);
        System.out.println(imageStr);

//        HttpSession session = request.getSession();
//        session.setAttribute("captcha", token);
//


        captchaDto.setSecret("car4y8cryaw84cr89awnrc");
        captchaDto.setImage("data:image/png;base64, код_изображения_в_base64");
        return captchaDto;
    }
}
