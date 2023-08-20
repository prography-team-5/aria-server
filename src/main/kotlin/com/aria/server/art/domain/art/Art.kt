package com.aria.server.art.domain.art

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE art SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
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

    @OneToMany(mappedBy = "art", cascade = [CascadeType.ALL], orphanRemoval = true)
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

    fun changeTitle(title: String) {
        this.title = title
    }

    fun changeMainImage(mainImage: ArtImage) {
        this.mainImage = mainImage
    }

    fun changeImages(images: List<ArtImage>) {
        this.images = images.toMutableList()
    }

    fun changeStyle(style: String) {
        this.style = style
    }

    fun changeYear(year: Int) {
        this.year = year
    }

    fun changeArtTags(artTags: List<ArtTag>) {
        this.artTags = artTags.toMutableList()
    }

    fun changeSize(size: Size) {
        this.size = size
    }

    fun changeDescription(description: String) {
        this.description = description
    }
}
