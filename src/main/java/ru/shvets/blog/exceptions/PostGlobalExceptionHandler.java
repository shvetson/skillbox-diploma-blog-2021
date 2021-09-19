package ru.shvets.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PostIncorrectData> handleException(NoSuchPostException e){
        PostIncorrectData data = new PostIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PostIncorrectData> handleException(Exception e){
        PostIncorrectData data = new PostIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}