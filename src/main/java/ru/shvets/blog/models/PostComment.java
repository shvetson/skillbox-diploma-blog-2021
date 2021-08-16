package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="post_comments")
public class PostComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @OneToOne
    @JoinColumn(name="parent_id")
    private PostComment parent;

    @Column(name="post_id", nullable = false)
    private long postId;

    @Column(name="user_id", nullable = false)
    private long userId;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false, columnDefinition = "text")
    private String text;
}
