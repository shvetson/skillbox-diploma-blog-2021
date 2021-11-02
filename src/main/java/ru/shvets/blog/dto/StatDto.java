package ru.shvets.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatDto {
    private int postsCount;
    private int likesCount;
    private int dislikesCount;
    private int viewsCount;
    @Temporal(TemporalType.TIMESTAMP)
    private long firstPublication;
}
