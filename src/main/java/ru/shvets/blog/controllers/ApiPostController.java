package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.PostResponse;
import ru.shvets.blog.services.PostService;

import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class ApiPostController {
    private final PostService postService;

    @GetMapping(value = {"", "/{offset}/{limit}/{mode}"})
    public ResponseEntity<PostResponse> getAllPosts(@PathParam("offset") int offset,
                                                    @PathParam("limit") int limit,
                                                    @PathParam("mode") String mode) {
        return ResponseEntity.ok(postService.getAllPosts(offset, limit, mode));
    }
}