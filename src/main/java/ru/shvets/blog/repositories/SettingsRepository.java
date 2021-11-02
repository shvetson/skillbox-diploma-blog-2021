package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.GlobalSettings;

@Repository
public interface SettingsRepository extends JpaRepository<GlobalSettings, Long> {
    @Query(nativeQuery = true, value = "select value from global_settings where code = 'STATISTICS_IS_PUBLIC'")
    String getValueByCode();
}
