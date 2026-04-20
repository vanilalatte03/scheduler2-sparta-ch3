package com.vanilalatte.scheduler.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank
    @Size(max = 200)
    private String content;
}
