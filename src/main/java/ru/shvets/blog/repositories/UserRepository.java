package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    boolean existsByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
}
