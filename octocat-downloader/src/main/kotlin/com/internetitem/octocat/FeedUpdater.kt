package com.internetitem.octocat

import com.internetitem.octocat.dataModel.Configuration
import java.io.File
import java.util.*

class FeedUpdater(val feedFetcher: FeedFetcher, val storage: Storage) {
    fun updateFeed(config: Configuration): Boolean {
        val feed = Downloader(feedFetcher).downloadFeed(config.feedUrl)
        val newFeedJson = feed.toJson()

        val feedFilename = File(config.destDir, Configuration.INDEX_FILE).path
        val oldFeedJson = storage.fetch(feedFilename)

        if (Arrays.equals(newFeedJson, oldFeedJson)) {
            return false
        } else {
            storage.store(feedFilename, newFeedJson)
            return true
        }
    }
}