package com.vanilalatte.scheduler.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String userName;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    public UpdateScheduleResponse(Long id, String title, String content, String userName, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }
}
