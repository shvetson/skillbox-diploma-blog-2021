package ru.shvets.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class FileUtils {

    // необходимо считать переменную из файла настроек, которая указывает место хранения картинок

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}

// необходимо написать методы по работе с фото пользователей
// при регистрации нового пользователя