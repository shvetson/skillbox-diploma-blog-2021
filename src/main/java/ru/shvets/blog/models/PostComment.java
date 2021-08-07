package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="post_comments")
public class PostComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;

    @OneToOne
    @JoinColumn(name="parent_id")
    private PostComment parent;

    @Column(name="post_id", nullable = false)
    private long postId;

    @Column(name="user_id", nullable = false)
    private long userId;

    @Column(name="time", nullable = false)
    private Date time;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;
}
