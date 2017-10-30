package com.internetitem.octocat.dataModel

import com.internetitem.octocat.util.toJson
import java.time.ZonedDateTime

data class OctocatFeed(val entries : List<OctocatFeedItem>) {
    data class OctocatFeedItem(val title : String, val link : String, val updated : ZonedDateTime, val imageUrl : String)

    fun toJson() = toJson(this)
}