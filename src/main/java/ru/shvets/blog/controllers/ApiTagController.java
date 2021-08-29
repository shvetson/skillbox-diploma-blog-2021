package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.TagResponse;
import ru.shvets.blog.services.TagService;

@RestController
@AllArgsConstructor
@RequestMapping("api/tag")
public class ApiTagController {
    private final TagService tagService;

//    @GetMapping(value = {"", "/{query}"})
//    public TagResponse getAllTags(@PathVariable Optional<String> query) {
//        return tagService.getAllTags(query.orElse(""));
//    }

    @GetMapping(value = {"", "/{query}"})
    public ResponseEntity<TagResponse> getAllTags(@RequestParam(name = "query", required = false, defaultValue = "") String  query){
        return ResponseEntity.ok(tagService.getAllTags(query));
    }
}