package com.aria.server.art.application.usecase

import com.aria.server.art.domain.sociallink.SocialLink

interface SocialLinkService {
    fun createSocialLink(socialLink: SocialLink)
    fun deleteSocialLink(id: Long)
    fun updateSocialLink(id: Long, url: String)
    fun getSocialLinks(artistId: Long): MutableList<SocialLink>
}