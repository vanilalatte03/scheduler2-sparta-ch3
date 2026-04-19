package com.vanilalatte.scheduler.comment.dto;

import com.vanilalatte.scheduler.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {

    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    private GetCommentResponse(Long id, String content, Long userId, Long scheduleId, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public static GetCommentResponse from(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
