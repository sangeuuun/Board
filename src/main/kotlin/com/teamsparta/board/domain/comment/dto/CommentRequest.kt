package com.teamsparta.board.domain.comment.dto

import com.teamsparta.board.domain.comment.model.Comment
import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.post.model.Post
import jakarta.validation.constraints.NotBlank

data class CommentRequest(
    @field:NotBlank
    val content: String
) {
    fun toEntity(member: Member, post: Post): Comment {
        return Comment(
            content = this.content,
            member = member,
            post = post
        )
    }
}