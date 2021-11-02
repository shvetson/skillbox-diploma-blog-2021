package ru.shvets.blog.exceptions;

public class CaptchaException extends  RuntimeException{
    public CaptchaException(String message) {
        super(message);
    }
}