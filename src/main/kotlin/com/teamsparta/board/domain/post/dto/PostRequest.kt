package com.teamsparta.board.domain.post.dto

import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.post.model.Post
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PostRequest(
    @field:NotBlank
    @field:Size(max = 500)
    val title: String,

    @field:NotBlank
    @field:Size(max = 5000)
    val content : String
)

fun PostRequest.toEntity(member: Member, fileUrl: String?): Post {
    return Post(
        title = this.title,
        content = this.content,
        member = member,
        file = fileUrl
    )
}
