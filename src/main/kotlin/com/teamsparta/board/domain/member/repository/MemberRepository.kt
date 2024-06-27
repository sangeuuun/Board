package com.teamsparta.board.domain.member.repository

import com.teamsparta.board.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun findByNickname(nickname: String): Member?
}