package com.aria.server.art.infrastructure.dto

import com.aria.server.art.domain.Size

data class GetRandomArtResponse(
    val artId: Long,
    val memberId: Long,
    val mainImageUrl: String, // TODO: 메인 이미지만 필요한지, 전체 이미지 목록이 필요한지 논의/확인 필요
    val title: String,
    val year: Int,
    val styles: List<String>,
    val size: Size,
    val description: String
)
