package com.teamsparta.board.domain.member.controller

import com.teamsparta.board.domain.member.dto.LoginRequest
import com.teamsparta.board.domain.member.dto.LoginResponse
import com.teamsparta.board.domain.member.dto.MemberResponse
import com.teamsparta.board.domain.member.dto.SignUpRequest
import com.teamsparta.board.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.signUp(request))
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(request))
    }
}