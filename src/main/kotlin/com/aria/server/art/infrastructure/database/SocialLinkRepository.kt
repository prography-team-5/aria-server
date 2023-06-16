package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.sociallink.SocialLink
import org.springframework.data.jpa.repository.JpaRepository

interface SocialLinkRepository: JpaRepository<SocialLink, Long> {
    fun findSocialLinksByMemberId(id: Long): MutableList<SocialLink>
    fun existsByUrl(url: String): Boolean
}