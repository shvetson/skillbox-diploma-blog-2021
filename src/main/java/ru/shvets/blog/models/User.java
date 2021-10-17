package ru.shvets.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Date regTime;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String code;

    @Column(columnDefinition = "text")
    private String photo;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
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
