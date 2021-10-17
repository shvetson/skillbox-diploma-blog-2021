package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.api.responses.InitResponse;
import ru.shvets.blog.dto.NewPostDto;
import ru.shvets.blog.dto.SettingsDto;
import ru.shvets.blog.services.SettingsService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;

    @GetMapping("/init")
    public InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    public SettingsDto settings() {
        return settingsService.getGlobalSettings();
    }

    @PostMapping("/image")
    public ErrorResponse Upload(@RequestParam("file") MultipartFile file) {
        return null;
    }
}
