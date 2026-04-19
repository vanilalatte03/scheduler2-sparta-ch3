package com.vanilalatte.scheduler.comment.controller;

import com.vanilalatte.scheduler.comment.dto.*;
import com.vanilalatte.scheduler.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comment")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(scheduleId, loginUserId, request));
    }

    @GetMapping("/schedules/{scheduleId}/comment")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.ok().body(commentService.getAll(scheduleId));
    }

    @PatchMapping("comment/{commentId}")
    private ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return ResponseEntity.ok().body(commentService.update(commentId, loginUserId, request));
    }

    @DeleteMapping("comment/{commentId}")
    private ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        commentService.delete(commentId, loginUserId);
        return ResponseEntity.noContent().build();
    }





}
