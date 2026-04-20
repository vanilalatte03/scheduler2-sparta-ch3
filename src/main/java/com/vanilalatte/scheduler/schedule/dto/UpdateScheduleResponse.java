package com.vanilalatte.scheduler.schedule.dto;

import com.vanilalatte.scheduler.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    private UpdateScheduleResponse(Long id, String title, String content, Long userId, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public static UpdateScheduleResponse from(Schedule schedule) {
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
