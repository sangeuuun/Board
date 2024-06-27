package com.teamsparta.board.domain.comment.model

import com.teamsparta.board.domain.comment.dto.CommentRequest
import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.post.model.Post
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @Column(name = "content", nullable = false)
    var content : String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = true)
    var updatedAt: LocalDateTime? = null

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateComment(request: CommentRequest){
        content = request.content
        updatedAt = LocalDateTime.now()
    }
}