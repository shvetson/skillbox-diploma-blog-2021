package ru.shvets.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.PostVote;

import java.util.List;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    List<PostVote> findByPostId(long postId);

    List<PostVote> findByPostIdAndValue(long postId, byte value);

    int countFindByPostIdAndValue(long postId, byte value);

    @Query(nativeQuery = true, value = "select count(*) count from post_votes where post_id in (select id from posts where user_id = ?1) and value = ?2")
    Integer countPostVotesByUserIdAndValue(long userId, byte value);

    Integer countPostVotesByValueEquals(byte value);
    PostVote findPostVoteByUserIdAndPostId(Long userId, Long postId);
}
