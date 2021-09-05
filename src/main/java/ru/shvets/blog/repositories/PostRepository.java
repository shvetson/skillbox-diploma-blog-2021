package ru.shvets.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.Post;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findByModerationStatus(ModerationStatus moderationStatus);

    @Query(nativeQuery = true, value = "select distinct year(time) year from posts order by year")
    List<Integer> findAllYears();

    @Query(nativeQuery = true, value = "select date(p.time) as date, count(*) as count from posts as p where p.is_active=1 and p.moderation_status='ACCEPTED' and date(p.time) between ?1 and ?2 group by date")
    List<Object> countPostsByDate(Date after, Date before);

    @Query(nativeQuery = true, value = "select * from posts as p where p.is_active=1 and p.moderation_status='ACCEPTED' and p.time between ?1 and ?2")
    Page<Post> findByIsActiveAndModerationStatusAndDate(Date dateAfter, Date dateBefore, Pageable pageable);

    Page<Post> findByIsActiveAndModerationStatusAndTitleContaining(byte isActive, ModerationStatus moderationStatus, String title, Pageable page);

    Page<Post> findAllByIsActiveAndModerationStatus(byte isActive, ModerationStatus moderationStatus, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from posts where is_active=1 and moderation_status='ACCEPTED'")
    Page<Post> findAllIsActiveAndIsAccepted(Pageable pageable);

    @Query(nativeQuery = true, value = "select a.*, count(b.value) as votes from posts as a join post_votes as b on a.id=b.post_id where a.is_active=1 and a.moderation_status='ACCEPTED' group by a.id")
    Page<Post> findAllIsActiveAndIsAcceptedAndVotes(Pageable pageable);

    @Query(nativeQuery = true, value = "select a.*, b.comments from posts as a left join (select post_id, count(post_id) as comments from post_comments group by post_id) as b on a.id=b.post_id\n" +
            "where a.is_active=1 and a.moderation_status='ACCEPTED'")
    Page<Post> findAllIsActiveAndIsAcceptedAndComments(Pageable pageable);

    @Query(nativeQuery = true, value = "select a.* from posts as a join tag2post as b on a.id=b.post_id join tags as c on c.id=b.tag_id where a.is_active=1 and a.moderation_status='ACCEPTED' and c.name=?1")
    Page<Post> findByIsActiveAndModerationStatusAndTag(String tag, Pageable pageable);
}