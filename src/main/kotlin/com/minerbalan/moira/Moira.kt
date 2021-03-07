package com.minerbalan.moira

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class MoiraApplication

fun main(args: Array<String>) {
    runApplication<MoiraApplication>(*args)
}
