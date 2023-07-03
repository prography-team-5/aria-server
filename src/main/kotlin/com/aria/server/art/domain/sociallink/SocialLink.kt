package com.aria.server.art.domain.sociallink

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.member.PlatformType
import javax.persistence.*

@Entity
class SocialLink (
    url: String,
    socialType: SocialType,
    member: Member
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var url: String = url
        protected set

    @Column(nullable = false)
    var socialType: SocialType = socialType
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    fun changeUrl(url: String) {
        this.url = url
    }
}