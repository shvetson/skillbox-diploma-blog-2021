package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.PostVote;

import java.util.List;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    List<PostVote> findByPostId(long postId);
    List<PostVote> findByPostIdAndValue(long postId, byte value);
    int countFindByPostIdAndValue(long postId, byte value);
}
