package com.teamsparta.board.domain.post.controller

import com.teamsparta.board.domain.post.dto.PageResponse
import com.teamsparta.board.domain.post.dto.PostRequest
import com.teamsparta.board.domain.post.dto.PostResponse
import com.teamsparta.board.domain.post.service.PostService
import com.teamsparta.board.infra.security.UserPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService,
) {

    @GetMapping
    fun getPostList(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "createdAt") sortBy: String,
        @RequestParam("sort_direction", defaultValue = "desc") direction: String,
    ): ResponseEntity<PageResponse<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList(page, size, sortBy, direction))
    }

    @GetMapping("/{postId}")
    fun getPostById(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid @RequestPart("request") request: PostRequest,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(principal.id, request, file))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid @RequestPart("request") request: PostRequest,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, principal.id, request, file))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(postId, principal.id))
    }

    @PostMapping("/{postId}/like")
    fun postLikes(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        postService.postLikes(postId, principal.id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }
}