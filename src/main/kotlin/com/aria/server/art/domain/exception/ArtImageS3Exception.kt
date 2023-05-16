package com.aria.server.art.domain.exception

class ArtImageS3Exception(message: String? = "S3에 이미지 업로드를 실패했습니다."): RuntimeException(message)
