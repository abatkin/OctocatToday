package com.internetitem.octocat.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

internal fun Date.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
}
