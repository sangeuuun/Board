package com.teamsparta.board.domain.post.dto

import com.teamsparta.board.domain.comment.dto.CommentResponse
import com.teamsparta.board.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val file: String?,
    val nickname: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    var like: Int,
    val comments: List<CommentResponse> = emptyList()
) {
    companion object {
        fun createFrom(post: Post): PostResponse {
            return PostResponse(
                post.id ?: throw IllegalStateException("ID cannot be Null"),
                post.title,
                post.content,
                post.file,
                post.member.nickname,
                post.createdAt,
                post.updatedAt,
                post.likes.size
            )
        }
        fun from(post: Post): PostResponse {
            return PostResponse(
                post.id ?: throw IllegalStateException("ID cannot be Null"),
                post.title,
                post.content,
                post.file,
                post.member.nickname,
                post.createdAt,
                post.updatedAt,
                post.likes.size,
                post.comments.map { CommentResponse.from(it) }
            )
        }
    }
}
