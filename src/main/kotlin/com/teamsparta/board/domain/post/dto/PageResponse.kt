package com.teamsparta.board.domain.post.dto

data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int
)
