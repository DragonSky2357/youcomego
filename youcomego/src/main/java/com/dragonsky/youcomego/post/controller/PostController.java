package com.dragonsky.youcomego.post.controller;

import com.dragonsky.youcomego.post.dto.PostRequestDto;
import com.dragonsky.youcomego.post.dto.PostResponseDto;
import com.dragonsky.youcomego.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.findPost(id));
    }

    @PostMapping()
    public ResponseEntity savePost(Principal principal,@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(postService.savePost(principal,postRequestDto));
    }

    @PatchMapping()
    public ResponseEntity updatePost(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok().build();
    }
}
