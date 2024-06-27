package com.teamsparta.board.domain.likes.model

import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.post.model.Post
import jakarta.persistence.*

@Entity
@Table(name = "likes")
class Likes(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}