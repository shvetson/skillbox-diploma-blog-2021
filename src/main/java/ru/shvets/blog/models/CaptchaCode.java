package ru.shvets.blog.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="captcha_codes")
public class CaptchaCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="time", nullable = false)
    private Date time;

    @Column(name = "code", nullable = false, columnDefinition = "tinytext")
    private String code;

    @Column(name = "secret_code", nullable = false,  columnDefinition = "tinytext")
    private String secretCode;
}
