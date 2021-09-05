package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.YearResponse;
import ru.shvets.blog.services.PostService;

import java.text.ParseException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/calendar")
public class ApiCalendarController {
    private final PostService postService;

    @GetMapping(value = {"", "/{year}"})
    public ResponseEntity<Map<String, Object>> viewCalendar(@RequestParam(name = "year", required = false, defaultValue = "0") int year) throws ParseException {
        return ResponseEntity.ok(postService.getAllPostsByYear(year));
    }
}