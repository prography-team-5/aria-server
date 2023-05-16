package com.aria.server.art.domain.member

import com.aria.server.art.domain.AuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Member(
    email: String,
    nickname: String,
    profileImageUrl: String,
    role: Role,
    platformType: PlatformType
): AuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var nickname: String = nickname
        protected set

    @Column(nullable = false)
    var profileImageUrl: String = profileImageUrl
        protected set

    @Column(nullable = false)
    var role: Role = role
        protected set

    @Column(nullable = false)
    var platformType: PlatformType = platformType
        protected set

    fun changeProfileImageUrl(profileImageUrl: String) {
        this.profileImageUrl = profileImageUrl
    }

    fun changeRole(role: Role) {
        this.profileImageUrl = profileImageUrl
    }
}
