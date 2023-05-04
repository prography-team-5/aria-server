package com.aria.server.art.domain

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne


@Entity
class Art (
    title: String,
    imageUrl: String,
    year: Int,
    styles: MutableList<Style>,
    size: Size,
    description: String,
    member: Member
): BaseEntity() {
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set
}
