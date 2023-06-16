package com.aria.server.art.domain.report

import com.aria.server.art.domain.AuditEntity
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.sociallink.SocialType
import javax.persistence.*

@Entity
class MemberReport (
    reporterId: Long,
    reportedMemberId: Long,
    content: String
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var reporterId: Long = reporterId
        protected set

    @Column(nullable = false)
    var reportedMemberId: Long = reportedMemberId
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set
}