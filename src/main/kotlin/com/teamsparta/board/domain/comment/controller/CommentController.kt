package com.teamsparta.board.domain.comment.controller

import com.teamsparta.board.domain.comment.dto.CommentRequest
import com.teamsparta.board.domain.comment.dto.CommentResponse
import com.teamsparta.board.domain.comment.service.CommentService
import com.teamsparta.board.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping()
    fun createComment(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, principal.id, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody @Valid request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, principal.id, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        commentService.deleteComment(postId, commentId, principal.id)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}