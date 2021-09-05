package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.services.TagService;

import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/tag")
public class ApiTagController {
    private final TagService tagService;

    @GetMapping(value = {"", "/{query}"})
    public ResponseEntity<Map<String, Object>> getAllTags(@PathVariable Optional<String> query) {
        return ResponseEntity.ok(tagService.getAllTags(query.orElse("")));
    }

//    @GetMapping(value = {"", "/{query}"})
//    public ResponseEntity<Map<String, Object>>  getAllTags(@RequestParam(name = "query", required = false, defaultValue = "") String  query){
//        return ResponseEntity.ok(tagService.getAllTags(query));
//    }
}