package com.teamsparta.board.domain.post.repository

import com.teamsparta.board.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>, CustomPostRepository {
}