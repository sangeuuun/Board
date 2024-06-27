package com.teamsparta.board.domain.comment.repository

import com.teamsparta.board.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}