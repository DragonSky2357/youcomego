package com.dragonsky.youcomego.member.repository;

import com.dragonsky.youcomego.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Member> findByEmail(String email);
}
