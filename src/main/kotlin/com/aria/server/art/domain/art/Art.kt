package com.aria.server.art.domain.art

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.*


@Entity
class Art (
    title: String,
    imageUrl: String,
    year: Int,
    styles: MutableList<Style>,
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

    @Column(nullable = false)
    var imageUrl: String = imageUrl
        protected set

    @Column(nullable = false)
    var year: Int = year
        protected set

    @OneToMany(mappedBy = "art")
    var styles: MutableList<Style> = styles
        protected set

    @Embedded
    var size: Size = size
        protected set

    @Column(nullable = false)
    var description: String = description
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set
}
