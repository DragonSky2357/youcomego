package com.dragonsky.youcomego.member.service;

import com.dragonsky.youcomego.global.utils.jwt.JwtToken;
import com.dragonsky.youcomego.global.utils.jwt.JwtTokenProvider;
import com.dragonsky.youcomego.member.dto.CreateMemberDto;
import com.dragonsky.youcomego.member.dto.LoginDto;
import com.dragonsky.youcomego.member.model.Member;
import com.dragonsky.youcomego.member.model.MemberRole;
import com.dragonsky.youcomego.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(CreateMemberDto createMemberDto){
        if(memberRepository.existsByEmail(createMemberDto.getEmail())){
            throw new RuntimeException("이미 존재하는 사용자 입니다.");
        }

        if(memberRepository.existsByUsername(createMemberDto.getUsername())){
            throw new RuntimeException("이미 존재하는 사용자 이름 입니다.");
        }

        Member member = Member.builder()
                .email(createMemberDto.getEmail())
                .username(createMemberDto.getUsername())
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public String  logIn(LoginDto loginDto){
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 E-MAil 입니다."));
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(member.getUsername(),member.getRoles());
    }
}
