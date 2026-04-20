package com.vanilalatte.scheduler.user.dto;

import com.vanilalatte.scheduler.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {

    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;

    private UpdateUserResponse(Long id, String userName, String email, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public static UpdateUserResponse from(User user){
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
