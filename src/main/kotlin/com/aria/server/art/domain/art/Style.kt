package com.aria.server.art.domain.art

import javax.persistence.*

@Entity
class Style(
    name: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var name: String = name
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id")
    var art: Art? = null
        protected set
}
