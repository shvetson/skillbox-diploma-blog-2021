package ru.shvets.blog.validators;

import ru.shvets.blog.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {
    private final UserService userService;

    public IsEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isUser(value);
    }
}
