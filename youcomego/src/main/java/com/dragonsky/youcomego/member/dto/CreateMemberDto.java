package com.dragonsky.youcomego.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class CreateMemberDto {

    @NotBlank(message="이름은 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "이메일 필수 입력 값입니다.")
    @Email(message="이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 6, max = 20, message = "비밀번호는 6자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @Builder
    public CreateMemberDto(String username, String email, String password, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
