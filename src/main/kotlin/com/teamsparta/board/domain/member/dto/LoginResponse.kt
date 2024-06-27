package com.teamsparta.board.domain.member.dto

data class LoginResponse(
    val accessToken: String
) {
    companion object {
        fun from(token: String): LoginResponse {
            return LoginResponse(
                accessToken = token
            )
        }
    }
}
