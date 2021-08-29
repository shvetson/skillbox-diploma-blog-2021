package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
