package com.teamsparta.board.domain.post.service

import com.teamsparta.board.domain.post.dto.PostRequest
import com.teamsparta.board.domain.post.dto.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface PostService {
    fun getPostList(pageable: Pageable): Page<PostResponse>
    fun getPostById(postId: Long): PostResponse
    fun createPost(memberId: Long, request: PostRequest, file: MultipartFile?): PostResponse
    fun updatePost(postId: Long, memberId: Long, request: PostRequest, file: MultipartFile?): PostResponse
    fun deletePost(postId: Long, memberId: Long)
    fun postLikes(postId: Long, memberId: Long)
}