package com.vanilalatte.scheduler.comment.service;

import com.vanilalatte.scheduler.comment.dto.*;
import com.vanilalatte.scheduler.comment.entity.Comment;
import com.vanilalatte.scheduler.comment.repository.CommentRepository;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.schedule.service.ScheduleService;
import com.vanilalatte.scheduler.user.entity.User;
import com.vanilalatte.scheduler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    @Transactional
    public CreateCommentResponse create(Long scheduleId, Long loginUserId, CreateCommentRequest request) {
        Schedule schedule = scheduleService.findScheduleById(scheduleId);

        User user = userService.findUserById(loginUserId);

        Comment comment = new Comment(schedule, user, request.getContent());
        Comment saveComment = commentRepository.save(comment);

        return CreateCommentResponse.from(saveComment);

    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for(Comment comment : comments){
            dtos.add(GetCommentResponse.from(comment));
        }
        return dtos;
    }

    @Transactional
    public UpdateCommentResponse update(Long commentId, Long loginUserId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );

        if(!comment.getUser().getId().equals(loginUserId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        comment.update(request.getContent());
        return UpdateCommentResponse.from(comment);
    }

    @Transactional
    public void delete(Long commentId, Long loginUserId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글입니다.")
        );

        if(!comment.getUser().getId().equals(loginUserId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
