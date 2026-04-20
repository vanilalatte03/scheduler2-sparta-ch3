package com.vanilalatte.scheduler.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
