package ru.shvets.blog.exceptions;

public class NoSuchPostException extends  RuntimeException{
    public NoSuchPostException(String message) {
        super(message);
    }
}
