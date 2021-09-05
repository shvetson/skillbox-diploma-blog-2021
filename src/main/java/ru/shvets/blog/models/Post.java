package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagList;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "post_votes",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> userListVotes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<PostVote> listVotes = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "post_comments",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> userListComments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<PostComment> listComments = new ArrayList<>();
}
