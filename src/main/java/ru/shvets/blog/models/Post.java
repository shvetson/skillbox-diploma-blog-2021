package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "is_active", nullable = false)
    private byte isActive;

    //@Column(name = "moderation_status", nullable = false, columnDefinition = "enum")
    @Column(name = "moderation_status", nullable = false, columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED')")
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    @OneToOne
    @JoinColumn (name = "moderator_id")
    private User moderator;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_votes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userListVotes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_comments",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userListComments;
}
