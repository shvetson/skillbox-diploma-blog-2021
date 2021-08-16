package ru.shvets.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.InitResponse;
import ru.shvets.blog.api.responses.SettingsResponse;
import ru.shvets.blog.services.SettingsService;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    @Autowired
    private InitResponse initResponse;
    private SettingsService settingsService;

    @GetMapping("/init")
    public InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    public SettingsResponse settings() {
        return settingsService.getSettings();
    }
}
