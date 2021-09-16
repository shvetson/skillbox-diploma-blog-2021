package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="captcha_codes")
public class CaptchaCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;

    @Column(nullable = false, columnDefinition = "tinytext")
    private String code;

    @Column(name = "secret_code", nullable = false,  columnDefinition = "tinytext")
    private String secretCode;
}
