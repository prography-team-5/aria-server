package com.aria.server.art.application

import com.aria.server.art.infrastructure.ArtService
import com.aria.server.art.infrastructure.MemberService

class MemberServiceImpl(
    private val artService: ArtService
): MemberService {
}