package ru.shvets.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.shvets.blog.api.responses.ErrorResponse;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse();

        Map<String, Object> error = new LinkedHashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        response.setResult(false);
        response.setErrors(error);
        return response;
    }
}