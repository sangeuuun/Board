package com.teamsparta.board.domain.post.service

import com.teamsparta.board.domain.likes.service.LikesService
import com.teamsparta.board.domain.member.repository.MemberRepository
import com.teamsparta.board.domain.post.dto.PostRequest
import com.teamsparta.board.domain.post.dto.PostResponse
import com.teamsparta.board.domain.post.dto.toEntity
import com.teamsparta.board.domain.post.repository.PostRepository
import com.teamsparta.board.exception.ModelNotFoundException
import com.teamsparta.board.exception.UnauthorizedException
import com.teamsparta.board.infra.s3.S3Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val s3Service: S3Service,
    private val likesService: LikesService
): PostService {
    override fun getPostList(pageable: Pageable): Page<PostResponse> {
        TODO("Not yet implemented")
    }

    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")

        return PostResponse.from(post)
    }

    @Transactional
    override fun createPost(memberId: Long, request: PostRequest, file: MultipartFile?): PostResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("없는 사용자 입니다.")

        val fileUrl = file?.let { s3Service.upload(it) }

        return PostResponse.createFrom(postRepository.save(request.toEntity(member, fileUrl)))
    }

    @Transactional
    override fun updatePost(postId: Long, memberId: Long, request: PostRequest, file: MultipartFile?): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")

        if(memberId != post.member.id )
            throw UnauthorizedException("권한이 없습니다.")

        post.file?.let { s3Service.delete(it.split("m/")[1]) }
        val imageUrl = file?.let { s3Service.upload(it) }

        post.updatePost(request, imageUrl)

        return PostResponse.from(postRepository.save(post))
    }

    @Transactional
    override fun deletePost(postId: Long, memberId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")

        if (post.member.id != memberId)
            throw UnauthorizedException("권한이 없습니다.")

        postRepository.delete(post)
        likesService.delete(post)
        post.file?.let { s3Service.delete(it.split("m/")[1]) }
    }

    override fun postLikes(postId: Long, memberId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.")

        likesService.update(memberId, post)
    }
}