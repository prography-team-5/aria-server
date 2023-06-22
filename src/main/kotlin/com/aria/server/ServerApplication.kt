package com.aria.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:secure.properties")
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
