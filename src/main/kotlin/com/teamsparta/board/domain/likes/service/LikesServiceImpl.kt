package com.teamsparta.board.domain.likes.service

import com.teamsparta.board.domain.likes.model.Likes
import com.teamsparta.board.domain.likes.repository.LikesRepository
import com.teamsparta.board.domain.member.repository.MemberRepository
import com.teamsparta.board.domain.post.model.Post
import com.teamsparta.board.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikesServiceImpl(
    private val likesRepository: LikesRepository,
    private val memberRepository: MemberRepository
): LikesService {
    override fun update(memberId: Long, post: Post) {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("없는 사용자 입니다.")

        val reaction = likesRepository.findByPostId(post.id!!)
        if (post.member.id == memberId) throw IllegalArgumentException("본인이 작성한 글에는 남길 수 없습니다.")

        if (reaction == null) {
            likesRepository.save(
                Likes(
                    member = member,
                    post = post
                )
            )
        } else likesRepository.delete(reaction)
    }

    override fun delete(post: Post) {
        likesRepository.deleteAllByPostId(post.id!!)
    }
}