package com.vanilalatte.scheduler.schedule.dto;

import com.vanilalatte.scheduler.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    private GetScheduleResponse(Long id, String title, String content, Long userId, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public static GetScheduleResponse from(Schedule schedule) {
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
