package com.aria.server.art.domain.art

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class ArtImage(
    url: String,
    member: Member
): AuditEntity() {

    companion object {
        const val ART_IMAGE_URL_PREFIX = "https://bucket-8th-team5.s3.ap-northeast-2.amazonaws.com/"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    private var url: String = url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = true)
    var art: Art? = null
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    fun changeArt(art: Art) {
        this.art = art
    }

    fun getUrl(): String = ART_IMAGE_URL_PREFIX + url
}
