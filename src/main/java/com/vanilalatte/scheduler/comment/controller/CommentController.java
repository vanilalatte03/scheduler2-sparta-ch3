package com.vanilalatte.scheduler.comment.controller;

import com.vanilalatte.scheduler.comment.dto.*;
import com.vanilalatte.scheduler.comment.service.CommentService;
import com.vanilalatte.scheduler.global.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @RequestBody @Valid CreateCommentRequest request,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(scheduleId, loginUserId, request));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.ok().body(commentService.getAll(scheduleId));
    }

    @PatchMapping("/comments/{commentId}")
    private ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        return ResponseEntity.ok().body(commentService.update(commentId, loginUserId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    private ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        commentService.delete(commentId, loginUserId);
        return ResponseEntity.noContent().build();
    }
}
