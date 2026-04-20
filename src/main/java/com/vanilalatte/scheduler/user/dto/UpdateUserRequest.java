package com.vanilalatte.scheduler.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank
    @Size(max = 30)
    private String userName;

    @NotBlank
    @Email
    @Size(max = 60)
    private String email;
}
