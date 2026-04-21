package com.vanilalatte.scheduler.comment.service;

import com.vanilalatte.scheduler.comment.dto.*;
import com.vanilalatte.scheduler.comment.entity.Comment;
import com.vanilalatte.scheduler.comment.repository.CommentRepository;
import com.vanilalatte.scheduler.global.exception.CommentNotFoundException;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.schedule.service.ScheduleService;
import com.vanilalatte.scheduler.user.entity.User;
import com.vanilalatte.scheduler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    @Transactional
    public CreateCommentResponse create(Long scheduleId, Long loginUserId, CreateCommentRequest request) {
        Schedule schedule = scheduleService.getScheduleOrThrow(scheduleId);
        User user = userService.getUserOrThrow(loginUserId);
        Comment comment = new Comment(schedule, user, request.getContent());
        Comment saveComment = commentRepository.save(comment);
        return CreateCommentResponse.from(saveComment);
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        scheduleService.getScheduleOrThrow(scheduleId);

        return commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(GetCommentResponse::from)
                .toList();
    }

    @Transactional
    public UpdateCommentResponse update(Long commentId, Long loginUserId, UpdateCommentRequest request) {
        Comment comment = getCommentOrThrow(commentId);
        comment.validateOwner(loginUserId);
        comment.update(request.getContent());
        return UpdateCommentResponse.from(comment);
    }

    @Transactional
    public void delete(Long commentId, Long loginUserId) {
        Comment comment = getCommentOrThrow(commentId);
        comment.validateOwner(loginUserId);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment getCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("존재하지 않는 댓글입니다.")
        );
    }
}
