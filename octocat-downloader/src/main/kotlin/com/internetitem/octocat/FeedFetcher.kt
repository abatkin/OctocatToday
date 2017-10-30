package com.internetitem.octocat

import com.github.kittinunf.fuel.httpGet
import java.io.FileInputStream

interface FeedFetcher {
    fun fetch(url : String) : ByteArray
}

class HttpFeedFetcher : FeedFetcher {
    override fun fetch(url: String): ByteArray {
        val (_, _, result) = url.httpGet().response()
        result.fold({ value ->
            return value
        }, { error ->
            throw error.exception
        })
    }
}

class FileFeedFetcher : FeedFetcher {
    override fun fetch(url: String): ByteArray {
        return FileInputStream(url).use { it.readBytes() }
    }

}