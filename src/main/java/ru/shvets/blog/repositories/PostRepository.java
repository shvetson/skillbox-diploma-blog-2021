package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByModerationStatus(ModerationStatus moderationStatus);
    List<Post> findByIsActiveAndModerationStatus(byte isActive, ModerationStatus moderationStatus);
    int countFindByIsActiveAndModerationStatus(byte isActive, ModerationStatus moderationStatus);

}