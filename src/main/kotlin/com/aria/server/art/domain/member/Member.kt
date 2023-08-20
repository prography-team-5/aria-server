package com.aria.server.art.domain.member

import com.aria.server.art.domain.AuditEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
class Member(
    email: String,
    nickname: String,
    role: Role,
    platformType: PlatformType
) : AuditEntity() {

    companion object {
        const val PROFILE_IMAGE_URL_PREFIX = "https://bucket-8th-team5.s3.ap-northeast-2.amazonaws.com/"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var nickname: String = nickname
        protected set

    @Column(nullable = true)
    private var profileImageUrl: String? = null

    @Column(nullable = false)
    var role: Role = role
        protected set

    @Column(nullable = false)
    var platformType: PlatformType = platformType
        protected set

    fun changeProfileImageUrl(profileImageUrl: String) {
        this.profileImageUrl = profileImageUrl
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changeRole(role: Role) {
        this.role = role
    }

    fun deleteProfileImageUrl() {
        this.profileImageUrl = null
    }

    fun getProfileImageUrl() =
        profileImageUrl
            ?.let { PROFILE_IMAGE_URL_PREFIX + profileImageUrl }
            ?:(PROFILE_IMAGE_URL_PREFIX + "basic_member.jpg")

    fun isBasicProfileImage(): Boolean = profileImageUrl.isNullOrEmpty()
}
