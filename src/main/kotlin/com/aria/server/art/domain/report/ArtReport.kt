package com.aria.server.art.domain.report

import com.aria.server.art.domain.AuditEntity
import javax.persistence.*

@Entity
class ArtReport (
    reporterId: Long,
    reportedArtId: Long,
    content: String
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var reporterId: Long = reporterId
        protected set

    @Column(nullable = false)
    var reportedArtId: Long = reportedArtId
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set
}