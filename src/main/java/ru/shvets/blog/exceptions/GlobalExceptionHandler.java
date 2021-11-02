package ru.shvets.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse onMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ErrorResponse response = new ErrorResponse();

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("image", "Размер файла превышает допустимый размер");

        response.setResult(false);
        response.setErrors(error);
        return response;
    }

    @ExceptionHandler(TimeExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse onTimeExpiredException(TimeExpiredException e) {
        ErrorResponse response = new ErrorResponse();

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", e.getMessage());

        response.setResult(false);
        response.setErrors(error);
        return response;
    }

    @ExceptionHandler(CaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse onTimeExpiredException(CaptchaException e) {
        ErrorResponse response = new ErrorResponse();

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("captcha", e.getMessage());

        response.setResult(false);
        response.setErrors(error);
        return response;
    }
}