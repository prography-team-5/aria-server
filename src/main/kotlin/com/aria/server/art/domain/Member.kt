package com.aria.server.art.domain

import javax.persistence.*

@Entity
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var email: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: Role,
    var nickname: String,
    var profileImageUrl: String,
    @Enumerated(EnumType.STRING)
    var signType: SignType
)