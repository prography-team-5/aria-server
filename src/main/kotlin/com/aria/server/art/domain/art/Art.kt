package com.aria.server.art.domain.art

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.*

@Entity
class Art (
    title: String,
    mainImage: ArtImage,
    images: MutableList<ArtImage>,
    style: String,
    year: Int,
    tags: MutableList<ArtTag>,
    size: Size,
    description: String,
    member: Member
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var title: String = title
        protected set

    @OneToOne
    var mainImage: ArtImage = mainImage
        protected set

    @OneToMany(mappedBy = "art")
    var images: MutableList<ArtImage> = images
        protected set

    @Column(nullable = false)
    var style: String = style
        protected set

    @Column(nullable = false)
    var year: Int = year
        protected set

    @OneToMany(mappedBy = "art", cascade = [CascadeType.PERSIST])
    var artTags: MutableList<ArtTag> = tags
        protected set

    @Embedded
    var size: Size = size
        protected set

    @Column(nullable = false, length = 1000)
    var description: String = description
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set
}
