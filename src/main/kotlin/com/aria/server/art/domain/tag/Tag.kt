package com.aria.server.art.domain.tag

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import javax.persistence.*

@Entity
class Tag (
    name: String,
    member: Member
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var name: String = name
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set
}