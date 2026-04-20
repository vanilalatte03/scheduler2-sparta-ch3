package com.vanilalatte.scheduler.global.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException {

    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
