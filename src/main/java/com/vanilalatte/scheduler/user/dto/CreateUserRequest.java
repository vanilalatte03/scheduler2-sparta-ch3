package com.vanilalatte.scheduler.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank
    @Size(max = 30)
    private String userName;

    @NotBlank
    @Email
    @Size(max = 60)
    private String email;

    @NotBlank
    @Size(min = 8, max = 60, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
