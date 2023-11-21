package com.dragonsky.youcomego.post.service;

import com.dragonsky.youcomego.member.model.Member;
import com.dragonsky.youcomego.member.repository.MemberRepository;
import com.dragonsky.youcomego.post.dto.PostRequestDto;
import com.dragonsky.youcomego.post.dto.PostResponseDto;
import com.dragonsky.youcomego.post.model.Post;
import com.dragonsky.youcomego.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Override
    public PostResponseDto findPost(Long id) {
         Post findPost = postRepository.findById(id).orElseThrow(RuntimeException::new);

         PostResponseDto responseDto = PostResponseDto.builder()
                 .title(findPost.getTitle())
                 .content(findPost.getContent())
                 .build();

         return responseDto;
    }

    @Override
    public boolean savePost(Principal principal, PostRequestDto postRequestDto) {
        Member findMember = memberRepository.findByEmail(principal.getName())
                .orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다."));

        Post newPost = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .member(findMember)
                .build();


        postRepository.save(newPost);
        return true;
    }

    @Override
    public boolean updatePost(PostRequestDto postRequestDto) {
        return false;
    }

    @Override
    public boolean deletePost(Long id) {
        return false;
    }
}
