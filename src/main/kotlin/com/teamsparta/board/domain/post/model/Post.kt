package com.teamsparta.board.domain.post.model

import com.teamsparta.board.domain.comment.model.Comment
import com.teamsparta.board.domain.likes.model.Likes
import com.teamsparta.board.domain.member.model.Member
import com.teamsparta.board.domain.post.dto.PostRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = true)
    var updatedAt: LocalDateTime? = null,

    @Column(name = "file", nullable = true)
    var file: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<Likes> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun updatePost(request: PostRequest, fileUrl: String?) {
        title = request.title
        content = request.content
        file = fileUrl
        updatedAt = LocalDateTime.now()
    }
}