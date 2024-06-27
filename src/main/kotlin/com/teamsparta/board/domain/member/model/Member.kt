package com.teamsparta.board.domain.member.model

import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "member")
class Member(
    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = true)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun isValidPassword(rawPassword: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }
}