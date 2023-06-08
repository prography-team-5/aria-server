package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.TagService
import com.aria.server.art.domain.exception.tag.DuplicatedTagException
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.tag.Tag
import com.aria.server.art.infrastructure.database.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TagServiceImpl(
    private val tagRepository: TagRepository
): TagService {

    override fun createTag(tag: Tag) {
        if (tagRepository.existsByName(tag.name)) {
            throw DuplicatedTagException()
        } else {
            tagRepository.save(tag)
        }
    }

    override fun deleteTag(id: Long) {
        tagRepository.deleteById(id)
    }

    override fun getTags(memberId: Long): MutableList<Tag> =
        tagRepository.findTagsByMemberId(memberId)

}