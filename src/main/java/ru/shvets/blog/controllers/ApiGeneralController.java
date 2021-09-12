package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.InitResponse;
import ru.shvets.blog.dto.SettingsDto;
import ru.shvets.blog.services.SettingsService;

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
}
