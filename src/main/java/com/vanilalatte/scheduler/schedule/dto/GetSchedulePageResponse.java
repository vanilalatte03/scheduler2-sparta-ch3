package com.vanilalatte.scheduler.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetSchedulePageResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetSchedulePageResponse(Long id, String title, String content, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}
