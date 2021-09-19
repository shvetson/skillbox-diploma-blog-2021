package ru.shvets.blog.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostIncorrectData {
    private String info;
}
