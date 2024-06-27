package com.teamsparta.board.domain.likes.service

import com.teamsparta.board.domain.post.model.Post

interface LikesService {
    fun update(memberId: Long, post: Post)
    fun delete(post: Post)
}