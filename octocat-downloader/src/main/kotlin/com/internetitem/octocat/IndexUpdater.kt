package com.internetitem.octocat

import java.util.*

object IndexUpdater {
    fun updateIndex(source: String, destination: String): Boolean {
        val feedBytes = FeedSource.fetchFromSource(source)
        val parsedFeed = FeedParser.toOctocatIndex(feedBytes)
        val newIndexJson = parsedFeed.toJson()

        val oldIndexJson = IndexStorage.fetchFromPath(destination)
        if (Arrays.equals(newIndexJson, oldIndexJson)) {
            return false
        } else {
            IndexStorage.storeToPath(destination, newIndexJson)
            return true
        }
    }
}