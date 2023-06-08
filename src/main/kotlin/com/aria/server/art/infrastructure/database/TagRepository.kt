package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
    fun existsByName(name: String): Boolean
    fun findTagsByMemberId(id: Long): MutableList<Tag>
}