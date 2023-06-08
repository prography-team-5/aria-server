package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.SocialLinkService
import com.aria.server.art.domain.exception.sociallink.DuplicatedSocialLinkException
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.sociallink.SocialLink
import com.aria.server.art.infrastructure.database.SocialLinkRepository
import org.springframework.stereotype.Service

@Service
class SocialLinkServiceImpl (
    private val socialLinkRepository: SocialLinkRepository
): SocialLinkService {

    override fun createSocialLink(socialLink: SocialLink) {
        if (socialLinkRepository.existsByUrl(socialLink.url)) {
            throw DuplicatedSocialLinkException()
        } else {
            socialLinkRepository.save(socialLink)
        }
    }

    override fun deleteSocialLink(id: Long) {
        socialLinkRepository.deleteById(id)
    }

    override fun updateSocialLink() {
        TODO("Not yet implemented")
    }

    override fun getSocialLinks(artistId: Long): MutableList<SocialLink> =
        socialLinkRepository.findSocialLinksByMemberId(artistId)


}