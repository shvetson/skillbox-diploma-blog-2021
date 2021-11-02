package ru.shvets.blog.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shvets.blog.api.responses.CommentResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.api.responses.InitResponse;
import ru.shvets.blog.dto.*;
import ru.shvets.blog.services.PostService;
import ru.shvets.blog.services.SettingsService;
import ru.shvets.blog.services.UserService;
import ru.shvets.blog.utils.FileUtils;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiGeneralController {
    @Value("${web.upload-path}")
    private String path;

    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final FileUtils fileUtils;
    private final PostService postService;
    private final UserService userService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, FileUtils fileUtils, PostService postService, UserService userService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.fileUtils = fileUtils;
        this.postService = postService;
        this.userService = userService;
    }

    //Общие данные блога
    @GetMapping("/init")
    public InitResponse init() {
        return initResponse;
    }

    //Получение настроек
    @GetMapping("/settings")
    public SettingsDto settings() {
        return settingsService.getGlobalSettings();
    }

    //Загрузка изображений
    @PostMapping("/image")
    public String uploadFile(@RequestParam(name = "file", required = true) MultipartFile multipartFile) throws Exception {
        return fileUtils.uploadFile(multipartFile, path, 3, 2);
    }

    //Отправка комментариев к посту
    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody NewCommentDto newCommentDto) throws Exception {
        return ResponseEntity.ok(postService.addComment(newCommentDto));
    }

    //Модерация поста
    // accept, decline
    @PostMapping("/moderation")
    public ResponseEntity<ErrorResponse> approvePost(@RequestBody PostStatusDto postStatusDto) {
        return ResponseEntity.ok(postService.updateStatusPost(postStatusDto));
    }

    //Редактирование моего профиля
    // проверка не работает?
    @PostMapping("/profile/my")
    public ResponseEntity<ErrorResponse> updateProfile(@ModelAttribute UserUpdatedDto userUpdatedDto) throws Exception {
        return ResponseEntity.ok(userService.updateProfile(userUpdatedDto));
    }

    //Моя статистика
    @GetMapping("/statistics/my")
    public ResponseEntity<StatDto> viewStatistics() {
        return ResponseEntity.ok(postService.getStatistics());
    }

    //Статистика по всему блогу
    @GetMapping("/statistics/all")
    public ResponseEntity<StatDto> viewAllStatistics() {
        return ResponseEntity.ok(postService.getAllStatistics());
    }
}