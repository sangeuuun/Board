package com.teamsparta.board.domain.comment.service

import com.teamsparta.board.domain.comment.dto.CommentRequest
import com.teamsparta.board.domain.comment.dto.CommentResponse

interface CommentService {
    fun createComment(postId: Long, memberId: Long, request: CommentRequest): CommentResponse
    fun updateComment(postId: Long, commentId: Long, memberId: Long, request: CommentRequest): CommentResponse
    fun deleteComment(postId: Long, commentId: Long, memberId: Long)
}