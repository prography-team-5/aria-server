package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetMemberProfileResponseDto
import org.springframework.web.multipart.MultipartFile

interface MemberUseCase {
    fun getMemberProfile(id: Long): GetMemberProfileResponseDto
    fun getMyProfile(): GetMemberProfileResponseDto
    fun editNickname(dto: EditNicknameRequestDto)
    fun changeRoleToArtist(id: Long)
    fun changeProfileImage(image: MultipartFile)
    fun deleteProfileImage()
}