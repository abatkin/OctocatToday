package com.internetitem.octocat

import com.internetitem.octocat.util.INDEX_FILE
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        fail()
    }

    val (feedUrl, destDir) = args

    if (FeedUpdater.updateFeed(feedUrl, File(destDir, INDEX_FILE).path)) {
        println("Feed updated")
    } else {
        println("Feed not updated")
    }
}

fun fail() {
    System.err.println("Usage: CommandLineRunner url destdir")
    exitProcess(-1)
}
