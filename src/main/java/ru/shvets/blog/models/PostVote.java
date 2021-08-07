package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="post_votes")
public class PostVote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "post_id", nullable = false)
    private long postId;

    @Column(name="time", nullable = false)
    private Date time;

    @Column(name = "value", nullable = false)
    private byte value;
}
