package com.internetitem.octocat

import java.util.*

object FeedUpdater {
    fun updateFeed(source: String, destination: String): Boolean {
        val feedBytes = FeedSource.fetchFromSource(source)
        val parsedFeed = FeedParser.parseFeed(feedBytes)
        val newFeedJson = parsedFeed.toJson()

        val oldFeedJson = Storage.FilesystemStorage.fetch(destination)
        if (Arrays.equals(newFeedJson, oldFeedJson)) {
            return false
        } else {
            Storage.FilesystemStorage.store(destination, newFeedJson)
            return true
        }
    }
}