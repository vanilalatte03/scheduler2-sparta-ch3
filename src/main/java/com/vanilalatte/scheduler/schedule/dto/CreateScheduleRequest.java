package com.vanilalatte.scheduler.schedule.dto;

import com.vanilalatte.scheduler.user.entity.User;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private Long userId;
    private String title;
    private String content;
}
