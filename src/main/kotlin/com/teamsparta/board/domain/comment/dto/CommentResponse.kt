package com.teamsparta.board.domain.comment.dto

import com.teamsparta.board.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val nickname: String,
    val createAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id ?: throw IllegalStateException("Comment Id cannot be null"),
                content = comment.content,
                nickname = comment.member.nickname,
                createAt = comment.createdAt,
                updatedAt = comment.updatedAt,
            )
        }
    }
}
