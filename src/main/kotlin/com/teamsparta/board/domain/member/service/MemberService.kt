package com.teamsparta.board.domain.member.service

import com.teamsparta.board.domain.member.dto.LoginRequest
import com.teamsparta.board.domain.member.dto.LoginResponse
import com.teamsparta.board.domain.member.dto.MemberResponse
import com.teamsparta.board.domain.member.dto.SignUpRequest

interface MemberService {
    fun signUp(request: SignUpRequest): MemberResponse
    fun login(request: LoginRequest): LoginResponse
}