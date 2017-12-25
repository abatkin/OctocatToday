package com.internetitem.octocat

import com.github.kittinunf.fuel.httpGet
import java.io.FileInputStream

interface FeedSource {
    fun fetch(url: String): ByteArray

    companion object {
        fun fromPath(path: String) = when {
            path.startsWith("http://") || path.startsWith("https://") -> HttpFeedSource
            else -> FileFeedSource
        }
    }

    object FileFeedSource : FeedSource {
        override fun fetch(url: String): ByteArray {
            return FileInputStream(url).use { it.readBytes() }
        }
    }

    object HttpFeedSource : FeedSource {
        override fun fetch(url: String): ByteArray {
            val (_, _, result) = url.httpGet().response()
            result.fold({ value ->
                return value
            }, { error ->
                throw error.exception
            })
        }
    }

}