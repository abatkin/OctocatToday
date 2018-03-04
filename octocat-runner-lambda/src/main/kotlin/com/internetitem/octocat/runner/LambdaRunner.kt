package com.internetitem.octocat.runner

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent
import com.internetitem.octocat.IndexUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.bridge.SLF4JBridgeHandler

class LambdaRunner {
    fun updateOctocats(event: ScheduledEvent, context: Context) {
        var fetchUrl = getParameter("FETCH_URL")
        var indexFile = getParameter("INDEX_FILE")

        LOGGER.info("Running! fetchUrl=${fetchUrl}, indexFile=${indexFile}")

        if (IndexUpdater.updateIndex(fetchUrl, indexFile)) {
            LOGGER.info("Index updated")
        } else {
            LOGGER.info("Index not updated")
        }
    }

    private fun getParameter(name: String)= System.getenv(name) ?: throw RuntimeException("Missing environment variable ${name}")

    companion object {
        val LOGGER: Logger

        init {
            SLF4JBridgeHandler.removeHandlersForRootLogger()
            SLF4JBridgeHandler.install()

            LOGGER = LoggerFactory.getLogger(LambdaRunner::class.java)
        }
    }
}

