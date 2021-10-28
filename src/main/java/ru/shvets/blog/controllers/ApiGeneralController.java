package ru.shvets.blog.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shvets.blog.api.responses.InitResponse;
import ru.shvets.blog.dto.SettingsDto;
import ru.shvets.blog.services.SettingsService;
import ru.shvets.blog.utils.FileUtils;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    @Value("${web.upload-path}")
    private String path;

    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final FileUtils fileUtils;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, FileUtils fileUtils) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.fileUtils = fileUtils;
    }

    //Общие данные блога
    @GetMapping("/init")
    public InitResponse init() {
        return initResponse;
    }

    //Получение настроек
    @GetMapping("/settings")
    public SettingsDto settings() {
        return settingsService.getGlobalSettings();
    }

    //Загрузка изображений
    @PostMapping("/image")
    public String uploadFile(@RequestParam(name = "file", required = true) MultipartFile multipartFile) throws Exception {
        return  fileUtils.uploadFile(multipartFile, path, 3, 2);
    }
}
