package com.aria.server.art.application.usecase

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.tag.Tag

interface TagService {
    fun createTag(tag: Tag)
    fun deleteTag(id: Long)
    fun getTags(artistId: Long): MutableList<Tag>
}