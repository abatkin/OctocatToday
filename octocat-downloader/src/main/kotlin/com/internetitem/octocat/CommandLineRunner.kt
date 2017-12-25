package com.internetitem.octocat

import kotlin.system.exitProcess

fun main(args: Array<String>) {
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

fun fail() {
    System.err.println("Usage: CommandLineRunner OctodexAtomUrl IndexLocation")
    exitProcess(-1)
}
