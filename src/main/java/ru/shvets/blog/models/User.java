package ru.shvets.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "is_moderator", nullable = false)
    @JsonIgnore
    private byte isModerator;

    @Column(name = "reg_time", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Date regTime;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @JsonIgnore
    @JsonView(Views.Email.class)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @JsonIgnore
    @ToString.Exclude
    private String code;

    @Column(columnDefinition = "text")
    @ToString.Exclude
    private String photo;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<PostComment> comments;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "post_votes",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "post_id"))
//    @JsonIgnore
//    private List<Post> postListVotes;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "post_comments",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "post_id"))
//    @JsonIgnore
//    private List<Post> postListComments;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }
}
