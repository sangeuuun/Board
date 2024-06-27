package com.teamsparta.board.domain.likes.repository

import com.teamsparta.board.domain.likes.model.Likes
import org.springframework.data.jpa.repository.JpaRepository

interface LikesRepository: JpaRepository<Likes, Long> {
    fun findByPostId(postId: Long): Likes?
    fun deleteAllByPostId(postId: Long)
}