package com.teamsparta.board.domain.member.dto

import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.member.model.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpRequest(

    @field:NotBlank
    @field:Size(min = 3, max = 10, message = "닉네임은 3 ~ 10자 사이 입니다.")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "닉네임은 영문과 숫자만 포함할 수 있습니다.")
    val nickname: String,

    @field:NotBlank
    @field:Size(min = 4, max = 15, message = "비밀번호는 4 ~ 15자 사이 입니다.")
    val password: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:Size(max = 50, message = "50자까지 작성 가능합니다.")
    val description: String?,

    val role: Role
) {
    fun toEntity(passwordEncoder: PasswordEncoder): Member {
        return Member(
            nickname = this.nickname,
            password = passwordEncoder.encode(this.password),
            email = this.email,
            description = this.description,
            role = this.role
        )
    }
}