package com.vanilalatte.scheduler.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역 예외를 공통 형식의 응답으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외를 상태 코드와 메시지 기반 에러 응답으로 변환합니다.
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception){
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    /**
     * Bean Validation 예외에서 첫 번째 필드 에러 메시지를 추출해 400 응답으로 변환합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        // 여러 필드 검증이 동시에 실패하더라도 첫 번째 에러 메시지만 응답에 사용한다.
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력 값이 올바르지 않습니다.");

        ErrorResponse response = new ErrorResponse(errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


}
