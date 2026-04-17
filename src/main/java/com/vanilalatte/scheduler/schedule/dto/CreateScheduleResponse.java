package com.vanilalatte.scheduler.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    public CreateScheduleResponse(Long id, String title, String content, Long userId, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }


}
