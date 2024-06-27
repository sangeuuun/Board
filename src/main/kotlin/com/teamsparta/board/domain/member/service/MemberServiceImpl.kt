package com.teamsparta.board.domain.member.service

import com.teamsparta.board.domain.member.dto.LoginRequest
import com.teamsparta.board.domain.member.dto.LoginResponse
import com.teamsparta.board.domain.member.dto.MemberResponse
import com.teamsparta.board.domain.member.dto.SignUpRequest
import com.teamsparta.board.domain.member.repository.MemberRepository
import com.teamsparta.board.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
): MemberService {
    @Transactional
    override fun signUp(request: SignUpRequest): MemberResponse {
        if (memberRepository.existsByEmail(request.email) || memberRepository.existsByNickname(request.nickname))
            throw IllegalStateException("이미 존재하는 회원입니다.")

        if (request.password.contains(request.nickname))
            throw IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다.")
        return MemberResponse.from(memberRepository.save(request.toEntity(passwordEncoder)))
    }

    override fun login(request: LoginRequest): LoginResponse {
        val member = memberRepository.findByNickname(request.nickname) ?: throw IllegalArgumentException("잘못된 Nickname/PW 입니다.")
        if (!member.isValidPassword(
                request.password,
                passwordEncoder
            )
        ) throw IllegalArgumentException("잘못된 Email/PW 입니다.")


        return LoginResponse.from(
            token = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                nickname = member.nickname,
                role = member.role.toString()
            )
        )
    }
}