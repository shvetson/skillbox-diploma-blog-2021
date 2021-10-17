package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.Tag2Post;

@Repository
public interface Tag2PostRepository extends JpaRepository<Tag2Post, Long>{
    Tag2Post save(Tag2Post tag2Post);
}
