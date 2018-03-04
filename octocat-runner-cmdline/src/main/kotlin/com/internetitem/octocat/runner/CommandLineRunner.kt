package com.internetitem.octocat.runner

import com.internetitem.octocat.IndexUpdater
import org.slf4j.bridge.SLF4JBridgeHandler
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    loggingInit()

    if (args.size != 2) {
        fail()
    }

    val (atomFeedUrl, indexLocation) = args

    if (IndexUpdater.updateIndex(atomFeedUrl, indexLocation)) {
        println("Index updated")
    } else {
        println("Index not updated")
    }
}

fun loggingInit() {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
}

fun fail() {
    System.err.println("Usage: CommandLineRunner OctodexAtomUrl IndexLocation")
    exitProcess(-1)
}
