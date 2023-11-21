package com.dragonsky.youcomego.post.service;

import com.dragonsky.youcomego.post.dto.PostRequestDto;
import com.dragonsky.youcomego.post.dto.PostResponseDto;

import java.security.Principal;

public interface PostService {
    public PostResponseDto findPost(Long id);

    public boolean savePost(Principal principal, PostRequestDto postRequestDto);

    public boolean updatePost(PostRequestDto postRequestDto);

    public boolean deletePost(Long id);
}
