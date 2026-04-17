package com.vanilalatte.scheduler.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String title;
    private String content;
    private String userName;
}
