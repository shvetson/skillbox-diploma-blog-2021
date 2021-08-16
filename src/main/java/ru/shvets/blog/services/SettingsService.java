package ru.shvets.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.SettingsResponse;
import ru.shvets.blog.models.GlobalSettings;
import ru.shvets.blog.repositories.SettingsRepository;

import java.util.Optional;

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public SettingsResponse getSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();

        Optional<GlobalSettings> multiuser = settingsRepository.findById((long) 1);
        Optional<GlobalSettings> preModeration = settingsRepository.findById((long) 2);
        Optional<GlobalSettings> statistics = settingsRepository.findById((long) 3);

        settingsResponse.setMultiUserMode(multiuser.isPresent());
        settingsResponse.setPostPreModeration(preModeration.isPresent());
        settingsResponse.setStatisticsIsPublic(statistics.isPresent());

        return settingsResponse;
    }
}
