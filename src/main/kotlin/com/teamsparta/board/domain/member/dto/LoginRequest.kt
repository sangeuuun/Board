package com.teamsparta.board.domain.member.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:NotBlank
    val nickname: String,

    @field:NotBlank
    val password: String
)