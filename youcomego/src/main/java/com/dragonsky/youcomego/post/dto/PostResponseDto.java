package com.dragonsky.youcomego.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;

    @Builder
    public PostResponseDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}