package com.teamsparta.board.domain.post.repository

import com.teamsparta.board.domain.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPostRepository {
    fun findByPageable(pageable: Pageable): Page<Post>
}