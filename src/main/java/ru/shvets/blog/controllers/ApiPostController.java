package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.dto.NewPostDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.dto.PostCommentDto;
import ru.shvets.blog.dto.PostCountDto;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.services.PostService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.text.ParseException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/post")
public class ApiPostController {
    private final PostService postService;

    //Список постов
    //recent, popular, best, early
    @GetMapping()
    public ResponseEntity<PostCountDto> getAllPosts(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                    @RequestParam(name = "mode", required = false, defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getAllPosts(offset, limit, mode));
    }

    //Поиск постов
    @GetMapping("/search")
    public ResponseEntity<PostCountDto> getAllPostsByQuery(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                           @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(postService.getAllPostsByQuery(offset, limit, query));
    }

    //Список постов за указанную дату
    @GetMapping("/byDate")
    public ResponseEntity<PostCountDto> getAllPostsByDate(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                          @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                          @RequestParam(name = "date", required = false, defaultValue = "") String date) throws ParseException {
        return ResponseEntity.ok(postService.getAllPostsByDate(offset, limit, date));
    }

    //Список постов по тэгу
    @GetMapping("/byTag")
    public ResponseEntity<PostCountDto> getAllPostsByTag(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                         @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                         @RequestParam(name = "tag", required = false, defaultValue = "") String tag) {
        return ResponseEntity.ok(postService.getAllPostsByTag(offset, limit, tag));
    }

    //Получение поста
    @GetMapping("/{id}")
    public ResponseEntity<PostCommentDto> getPostById(@PathVariable(name = "id") @Min(1) Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //Список постов на модерацию
    //new, accepted, declined
    @GetMapping("/moderation")
    public ResponseEntity<PostCountDto> getAllPostsByModerationStatus(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                                      @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                                      @RequestParam(name = "status", required = false, defaultValue = "NEW") ModerationStatus status) {
        return ResponseEntity.ok(postService.getAllPostByModerationStatus(offset, limit, status));
    }

    //Список моих постов
    //inactive, pending, declined, published
    @GetMapping("/my")
    public ResponseEntity<PostCountDto> getAllPostsByStatus(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                            @RequestParam(name = "status", required = false, defaultValue = "") String status) {
        return ResponseEntity.ok(postService.getAllPostByStatus(offset, limit, status));
    }

    //Добавление поста
    @PostMapping("")
    public ResponseEntity<ErrorResponse> addPost(@Valid @RequestBody NewPostDto newPostDto) {
        return ResponseEntity.ok(postService.addPost(newPostDto));
    }

    //Редактирование поста
    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updatePost(@PathVariable(name = "id") @Min(1) Long id, @Valid @RequestBody NewPostDto newPostDto) {
        return ResponseEntity.ok(postService.updatePost(id, newPostDto));
    }
}