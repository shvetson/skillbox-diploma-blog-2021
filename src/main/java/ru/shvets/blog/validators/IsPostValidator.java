package ru.shvets.blog.validators;

import ru.shvets.blog.repositories.PostRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPostValidator implements ConstraintValidator<IsPost, Long> {
    private final PostRepository postRepository;

    public IsPostValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return postRepository.getPostById(value) != null;
    }
}
