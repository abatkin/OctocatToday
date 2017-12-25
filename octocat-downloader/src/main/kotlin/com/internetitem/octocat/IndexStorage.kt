package com.internetitem.octocat

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

interface IndexStorage {
    fun fetch(name : String) : ByteArray?
    fun store(name : String, data : ByteArray)

    object FilesystemIndexStorage : IndexStorage {
        override fun fetch(name: String): ByteArray? {
            val file = File(name)
            if (file.exists() && file.canRead()) {
                return FileInputStream(file).use { it.readBytes() }
            }
            return null
        }

        override fun store(name: String, data: ByteArray) {
            val file = File(name)
            val dir = file.parentFile
            dir.mkdirs()
            FileOutputStream(file).use { it.write(data) }
        }
    }

}

