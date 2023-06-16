package com.aria.server.art.domain.artistinfo

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.*

@Entity
class ArtistInfo (
    member: Member,
    profileArtImageUrl: String?,
    intro: String?
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    @Column(nullable = true)
    var profileArtImageUrl: String? = profileArtImageUrl
        protected set

    @Column(nullable = true)
    var intro: String? = intro
        protected set

}