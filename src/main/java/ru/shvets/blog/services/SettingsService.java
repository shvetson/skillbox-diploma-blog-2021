package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.dto.SettingsDto;
import ru.shvets.blog.repositories.SettingsRepository;
import ru.shvets.blog.utils.MappingUtils;

@Service
@AllArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;
    private final MappingUtils mappingUtils;

    public SettingsDto getGlobalSettings() {
        return mappingUtils.mapToSettingsDto(settingsRepository.findAll());
    }
}