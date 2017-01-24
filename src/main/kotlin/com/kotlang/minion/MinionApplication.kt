package com.kotlang.minion

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.beans.configureMixins
import com.mashape.unirest.http.Unirest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.IOException

@SpringBootApplication
class MinionApplication (@Autowired objectMapper: ObjectMapper) {

    init {
        configureMixins(objectMapper)

        Unirest.setObjectMapper(object : com.mashape.unirest.http.ObjectMapper {

            override fun <T> readValue(value: String, valueType: Class<T>): T {
                try {
                    return objectMapper.readValue(value, valueType)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }

            }

            override fun writeValue(value: Any): String {
                try {
                    return objectMapper.writeValueAsString(value)
                } catch (e: JsonProcessingException) {
                    throw RuntimeException(e)
                }

            }
        })
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MinionApplication::class.java, *args)
}
