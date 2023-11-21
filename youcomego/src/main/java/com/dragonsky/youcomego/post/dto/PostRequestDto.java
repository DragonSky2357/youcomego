package com.dragonsky.youcomego.post.dto;


import com.dragonsky.youcomego.member.model.Member;
import com.dragonsky.youcomego.post.model.Post;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public  class PostRequestDto {
    private String title;
    private String content;
    @Builder
    public PostRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }


}
