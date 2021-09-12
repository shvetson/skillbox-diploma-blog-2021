package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.CaptchaCode;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Long> {
}
