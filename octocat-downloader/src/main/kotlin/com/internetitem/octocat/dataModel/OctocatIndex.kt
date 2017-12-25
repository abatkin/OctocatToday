package com.internetitem.octocat.dataModel

import com.internetitem.octocat.util.toJson
import java.time.ZonedDateTime

data class OctocatIndex(val entries : List<OctocatIndexItem>) {
    data class OctocatIndexItem(val title : String, val link : String, val updated : ZonedDateTime, val imageUrl : String)

    fun toJson() = toJson(this)
}