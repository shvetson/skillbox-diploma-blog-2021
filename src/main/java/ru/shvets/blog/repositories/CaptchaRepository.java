package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shvets.blog.models.CaptchaCode;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from captcha_codes where time_to_sec(timediff(now(), time)) > ?1")
    void deleteAllTimeGreaterThanHour(long timePeriod);

    CaptchaCode getCaptchaCodeBySecretCode(String secretCode);
}