package com.aria.server.art.domain.artistinfo

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.*

@Entity
class ArtistInfo (
    member: Member,
): AuditEntity() {

    companion object {
        const val PROFILE_ART_IMAGE_URL_PREFIX = "https://bucket-8th-team5.s3.ap-northeast-2.amazonaws.com/"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    @Column(nullable = true)
    private var profileArtImageUrl: String? = null

    @Column(nullable = true)
    var intro: String? = null

    fun changeProfileArtImageUrl(profileArtImageUrl: String) {
        this.profileArtImageUrl = profileArtImageUrl
    }

    fun changeIntro(intro: String) {
        this.intro = intro
    }

    fun getProfileArtImageUrl() = PROFILE_ART_IMAGE_URL_PREFIX + profileArtImageUrl
        ?: PROFILE_ART_IMAGE_URL_PREFIX + "basic_art.jpg"

    fun deleteProfileArtImageUrl() {
        this.profileArtImageUrl = null;
    }

    fun isBasicProfileArtImage(): Boolean = profileArtImageUrl.isNullOrEmpty()
}