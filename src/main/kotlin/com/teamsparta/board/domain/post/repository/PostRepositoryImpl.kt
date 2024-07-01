package com.teamsparta.board.domain.post.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import com.teamsparta.board.domain.post.model.Post
import com.teamsparta.board.domain.post.model.QPost
import com.teamsparta.board.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: CustomPostRepository, QueryDslSupport() {
    private val post = QPost.post

    override fun findByPageable(pageable: Pageable): Page<Post> {
        val whereClause = BooleanBuilder()

        // count의 경우 order와 무관하기 때문에 바로 수행
        val totalCount = queryFactory.select(post.count())
            .from(post)
            .where(whereClause)
            .fetchOne() ?: 0L

        val contents = queryFactory.selectFrom(post)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, post))
            .fetch()

        // Page 구현체 반환
        return PageImpl(contents, pageable, totalCount)
    }

    private fun getOrderSpecifier(pageable: Pageable, path: EntityPathBase<*>): Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property) as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}
