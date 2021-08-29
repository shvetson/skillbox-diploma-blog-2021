package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.SettingsResponse;
import ru.shvets.blog.models.GlobalSettings;
import ru.shvets.blog.repositories.SettingsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SettingsService {
    private  final SettingsRepository settingsRepository;

    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        List<GlobalSettings> settingsList = new ArrayList<>(settingsRepository.findAll());

        settingsResponse.setMultiUserMode(settingsList.stream().filter(a -> a.getCode().equals("MULTIUSER_MODE")).findFirst().get().getValue().equals("true"));
        settingsResponse.setPostPreModeration(settingsList.stream().filter(a -> a.getCode().equals("POST_PREMODERATION")).findFirst().get().getValue().equals("true"));
        settingsResponse.setStatisticsIsPublic(settingsList.stream().filter(a -> a.getCode().equals("STATISTICS_IS_PUBLIC")).findFirst().get().getValue().equals("true"));

        return settingsResponse;
    }
}
