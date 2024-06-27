package com.teamsparta.board.domain.member.dto

import com.teamsparta.board.domain.member.model.Member

data class MemberResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val description: String?
) {
    companion object {
        fun from(member: Member): MemberResponse {
            return MemberResponse(
                member.id ?: throw IllegalStateException("Member ID cannot be Null"),
                member.email,
                member.nickname,
                member.description
            )
        }
    }
}
