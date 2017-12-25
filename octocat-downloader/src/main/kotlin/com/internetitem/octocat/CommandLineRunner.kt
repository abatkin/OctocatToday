package com.internetitem.octocat

import com.internetitem.octocat.dataModel.Configuration
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        fail()
    }

    val (feedUrl, destDir) = args
    val feedFetcher = FeedSource.fromPath(feedUrl)
    val storage = Storage.FilesystemStorage
    val config = Configuration(feedUrl, destDir)

    val updater = FeedUpdater(feedFetcher, storage)
    if (updater.updateFeed(config)) {
        println("Feed updated")
    } else {
        println("Feed not updated")
    }
}

fun fail() {
    System.err.println("Usage: CommandLineRunner url destdir")
    exitProcess(-1)
}
