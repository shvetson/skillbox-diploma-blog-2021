package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.dto.PostCommentDto;
import ru.shvets.blog.dto.PostCountDto;
import ru.shvets.blog.services.PostService;

import javax.validation.constraints.Min;
import java.text.ParseException;

@RestController
@AllArgsConstructor

@RequestMapping("/api/post")
public class ApiPostController {
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<PostCountDto> getAllPosts(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                    @RequestParam(name = "mode", required = false, defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getAllPosts(offset, limit, mode));
    }

    @GetMapping("/search")
    public ResponseEntity<PostCountDto> getAllPostsByQuery(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                                  @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                                  @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(postService.getAllPostsByQuery(offset, limit, query));
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostCountDto> getAllPostsByDate(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                                 @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                                 @RequestParam(name = "date", required = false, defaultValue = "") String date) throws ParseException {
        return ResponseEntity.ok(postService.getAllPostsByDate(offset, limit, date));
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostCountDto> getAllPostsByTag(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                                @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                                @RequestParam(name = "tag", required = false, defaultValue = "") String tag) {
        return ResponseEntity.ok(postService.getAllPostsByTag(offset, limit, tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCommentDto> getPostById(@PathVariable(name = "id") @Min(1) Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }
}