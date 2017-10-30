package com.internetitem.octocat.dataModel

data class Configuration(val feedUrl : String, val destDir : String) {
    companion object {
        val INDEX_FILE = "octocat-index.json"
    }
}
