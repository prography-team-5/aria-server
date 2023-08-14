package com.aria.server.art.infrastructure.s3

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.aria.server.art.domain.exception.art.ArtImageS3Exception
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import java.io.InputStream


@Controller
class S3Api(
    private val s3Client: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String
) {

    fun saveImage(objectKey: String, metadata: ObjectMetadata, imageStream: InputStream): String {
        try {
            s3Client.putObject(bucketName, objectKey, imageStream, metadata)
        } catch (e: SdkClientException) {
            throw ArtImageS3Exception()
        } catch (e: AmazonServiceException) {
            throw ArtImageS3Exception()
        }

        return objectKey
    }

    fun deleteImage(imageName: String) {
        try {
            s3Client.deleteObject(DeleteObjectRequest(bucketName, imageName))
        } catch (e: SdkClientException) {
            throw ArtImageS3Exception()
        } catch (e: AmazonServiceException) {
            throw ArtImageS3Exception()
        }
    }
}
