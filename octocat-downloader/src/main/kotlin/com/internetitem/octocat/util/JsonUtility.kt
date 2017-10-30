package com.internetitem.octocat.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

val OBJECT_MAPPER: ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule())
        .registerModule(Jdk8Module())
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.INDENT_OUTPUT)

fun toJson(obj: Any) = OBJECT_MAPPER.writeValueAsBytes(obj)

