package com.teamsparta.board.domain.comment.service

import com.teamsparta.board.domain.comment.dto.CommentRequest
import com.teamsparta.board.domain.comment.dto.CommentResponse
import com.teamsparta.board.domain.comment.repository.CommentRepository
import com.teamsparta.board.domain.member.repository.MemberRepository
import com.teamsparta.board.domain.post.repository.PostRepository
import com.teamsparta.board.exception.ModelNotFoundException
import com.teamsparta.board.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
): CommentService {
    @Transactional
    override fun createComment(postId: Long, memberId: Long, request: CommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("없는 사용자 입니다.")

        return CommentResponse.from(commentRepository.save(request.toEntity(member, post)))
    }

    @Transactional
    override fun updateComment(
        postId: Long,
        commentId: Long,
        memberId: Long,
        request: CommentRequest
    ): CommentResponse {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("없는 댓글입니다.")

        if(memberId != comment.member.id )
            throw UnauthorizedException("권한이 없습니다.")

        comment.updateComment(request)
        return CommentResponse.from(comment)
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long, memberId: Long) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("없는 댓글입니다.")

        if(memberId != comment.member.id )
            throw UnauthorizedException("권한이 없습니다.")

        commentRepository.delete(comment)
    }
}