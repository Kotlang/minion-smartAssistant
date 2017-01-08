package com.kotlang.minion

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MinionApplication

fun main(args: Array<String>) {
    SpringApplication.run(MinionApplication::class.java, *args)
}
