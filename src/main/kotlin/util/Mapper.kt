package org.jameshpark.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val OBJECT_MAPPER = jacksonObjectMapper()

inline fun <reified T> deserializeFromJsonFile(path: String): T = OBJECT_MAPPER.readValue<T>(getResourceAsStream(path))
