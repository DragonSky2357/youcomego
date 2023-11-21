package com.dragonsky.youcomego.post.model;

import com.dragonsky.youcomego.global.entity.BaseTimeEntity;
import com.dragonsky.youcomego.member.model.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    Member member;

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

}
