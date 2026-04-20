package com.vanilalatte.scheduler.global.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ServiceException {

    public DuplicateEmailException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
