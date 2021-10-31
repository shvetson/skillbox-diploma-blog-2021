package ru.shvets.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileUtils {
//TODO необходимо написать методы по работе с фото пользователей при регистрации нового пользователя

    @Value("${web.photo-path}")
    private String photoDir;
    final int HEIGHT_IMAGE = 36;
    final int WIDTH_IMAGE = 36;

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

    public String resizeImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        //Проверка размера файла, если больше 36х36, то обрезка
        if (image.getHeight() > HEIGHT_IMAGE || image.getWidth() > WIDTH_IMAGE) {

            BufferedImage newImage = new BufferedImage(
                    WIDTH_IMAGE, HEIGHT_IMAGE, BufferedImage.TYPE_INT_RGB
            );

            int widthStep = image.getWidth() / WIDTH_IMAGE;
            int heightStep = image.getHeight() / HEIGHT_IMAGE;

            for (int x = 0; x < WIDTH_IMAGE; x++) {
                for (int y = 0; y < HEIGHT_IMAGE; y++) {
                    int rgb = image.getRGB(x * widthStep, y * heightStep);
                    newImage.setRGB(x, y, rgb);
                }
            }

            if (!photoDir.endsWith("/")) {
                photoDir = photoDir.concat("/");
            }
            String photoName = getUUID().replace("-", "") + ".jpg";
            String fileUrl = photoDir.concat(photoName);

            File newFile = new File(fileUrl);
            ImageIO.write(newImage, "jpg", newFile);
            return newFile.getAbsolutePath();
        }
        return null;
    }
}