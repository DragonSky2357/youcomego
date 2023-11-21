package com.dragonsky.youcomego.global.utils.jwt;

import com.dragonsky.youcomego.member.model.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "HelloWorld";

    private long tokenValidTime = 60 * 60 * 1000L;     // 토큰 유효시간 30분


    // UserDetailsService : 사용자 정보를 가져오는 인터페이스 인증 처리를 위해 사용 및
    // 사용자 정보를 데이터베이스에서 가져와서 Spring Security에 제공
    private final UserDetailsService userDetailsService;

    // @PostConstruct : 객체가 생성된 후에 자동으로 호출되는 메소드 DB연결, 외부 서비스 연결 설정등
    // 초기화 작업을 수행할때 사용
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk, List<String> roles){
        // Claims : 토큰에 포함되는 정보를 나타내는 부분
        // Subject : 일반적으로 사용자를 식별하는 데 사용
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles",roles);
        Date now = new Date();

        // compact : 여러줄이 아닌 한 줄로 간결하게 표현
        // URL 파라미터나 HTTP 헤더 등에서 쉽게 전송 및 처리할 수 있다.
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘과, secret 값
                .compact();
    }

    // Authentication : 신원 정보 및 권한 정보 포함
    public Authentication getAuthentication(String token){
        // loadUserByUsername : 사용자 이름을 기반으로 정보를 가져오는 역할 DB에서 사용자 정보를 가져와 인증
        // UsernamePasswordAuthenticationToken : 사용자 이름과 비밀번호를 가지고 인증을 수행할 때 사용된다.
        // 사용자 인증이 성공하면 Authentication를 생성하고 인증된 사용자의 정보와 권한을 담는다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        // parser : JwtParser 객체
        // setSigningKey : JWT의 서명을 확인하기 위한 비밀 키 설정
        // parseClaimsJws : 주어진 토큰을 파싱하고 서명이 유효한지 확인 유효하지 않으면 SignatureException
        // getBody : 토큰의 모든 클레임을 가져온다. Claims 객체로 표현
        // getSubject : Claims 객체에서 Subject를 가져온다.
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String jwtToken){
        try{
            // parseClaimsJws : 주어진 JWT를 파싱하고 서명을 확인한다.
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
            return false;
        }
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        if(request.getHeader("authorization") != null){
            return request.getHeader("authorization").substring(7);
        }
        return null;
    }
}
