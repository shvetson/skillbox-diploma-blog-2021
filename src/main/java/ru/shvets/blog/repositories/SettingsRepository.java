package ru.shvets.blog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.GlobalSettings;

@Repository
public interface SettingsRepository extends CrudRepository<GlobalSettings, Long> {
}
