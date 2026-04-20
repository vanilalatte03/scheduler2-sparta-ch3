package com.vanilalatte.scheduler.user.dto;

import com.vanilalatte.scheduler.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateUserResponse {

    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    private CreateUserResponse(Long id, String userName, String email, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public static CreateUserResponse from(User user){
        return new CreateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
