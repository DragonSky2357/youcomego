package com.dragonsky.youcomego.member.controller;

import com.dragonsky.youcomego.global.dto.ResultDto;
import com.dragonsky.youcomego.global.utils.jwt.JwtToken;
import com.dragonsky.youcomego.member.dto.CreateMemberDto;
import com.dragonsky.youcomego.member.dto.LoginDto;
import com.dragonsky.youcomego.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity createMember(@RequestBody @Valid CreateMemberDto createMemberDto) {
        try{
            memberService.signUp(createMemberDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResultDto<String>> logIn(@RequestBody @Valid LoginDto signInDto) {
        String token = memberService.logIn(signInDto);

        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), token));
    }
}
