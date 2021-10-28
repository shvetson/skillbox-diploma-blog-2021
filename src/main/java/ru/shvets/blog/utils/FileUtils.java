package ru.shvets.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class FileUtils {
//TODO необходимо написать методы по работе с фото пользователей при регистрации нового пользователя

    public String uploadFile(MultipartFile multipartFile, String pathUpload, int levelSub, int lengthNameSub) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new Exception("Файл не содержит данных");
        }

        String realPath = pathUpload + "/" + getPath(getExtension(multipartFile.getOriginalFilename()), levelSub, lengthNameSub);
        File dest = new File(realPath);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            log.info("Папки для хранения изображения созданы");
        }

        try {
            multipartFile.transferTo(dest);
            log.info("Файл загружен");
        } catch (Exception e) {
            throw new RuntimeException("Файл не сохраняется. Ошибка: " + e.getMessage());
        }
        return realPath;
    }

    private String getExtension(String fileName) throws Exception {
        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".png")) {
            throw new Exception("Формат изображения должен быть jpg или png");
        }
        return ext;
    }

    private String getPath(String fileExtension, int levelSub, int lengthNameSub) throws Exception {
        StringBuilder sb = new StringBuilder();

        String str = getUUID();
        int index = str.lastIndexOf("-");
        String strSub = str.substring(index + 1);
        int lengthStr = strSub.length();

        if (lengthNameSub * levelSub >= lengthStr) {
            throw new Exception("Необходимо изменить параметры - глубина вложений папок и (или) длину имени папок, по умолчанию - 3 и 2 соответственно");
        }

        for (int i = 1; i <= levelSub + 1; i++) {
            if (i == 1) {
                sb.append(strSub, 0, lengthNameSub * i).append("/");
                continue;
            } else if (i < levelSub + 1) {
                sb.append(strSub, lengthNameSub * (i - 1), lengthNameSub * i).append("/");
                continue;
            }
            sb.append(strSub, lengthNameSub * levelSub, lengthStr).append(fileExtension);
        }
        return sb.toString();
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }
}