package com.internetitem.octocat

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.AmazonS3URI
import com.amazonaws.services.s3.model.ObjectMetadata
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

interface IndexStorage {
    fun fetch(name : String) : ByteArray?
    fun store(name : String, data : ByteArray)

    companion object {
        fun fromPath(path: String) = when {
            path.startsWith("s3://") -> S3IndexStorage
            else -> FilesystemIndexStorage
        }

        fun fetchFromPath(path: String) = fromPath(path).fetch(path)
        fun storeToPath(path: String, data: ByteArray) = fromPath(path).store(path, data)
    }

    object FilesystemIndexStorage : IndexStorage {
        override fun fetch(name: String): ByteArray? {
            val file = File(name)
            if (file.exists() && file.canRead()) {
                return FileInputStream(file).use { it.readBytes() }
            }
            return null
        }

        override fun store(name: String, data: ByteArray) {
            val file = File(name)
            val dir = file.parentFile
            dir.mkdirs()
            FileOutputStream(file).use { it.write(data) }
        }
    }

    object S3IndexStorage : IndexStorage {

        val s3 = AmazonS3ClientBuilder.defaultClient()

        override fun fetch(name: String): ByteArray? {
            val s3Uri = AmazonS3URI(name)
            return s3.getObjectAsString(s3Uri.bucket, s3Uri.key).toByteArray()
        }

        override fun store(name: String, data: ByteArray) {
            val s3Uri = AmazonS3URI(name)
            val meta = ObjectMetadata()
            meta.contentLength = data.size.toLong()
            meta.contentType = "application/json"
            s3.putObject(s3Uri.bucket, s3Uri.key, ByteArrayInputStream(data), meta)
        }
    }
}

